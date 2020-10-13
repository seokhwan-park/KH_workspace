package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;

	@RequestMapping("blist.do")
	public ModelAndView boardList(ModelAndView mv,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		System.out.println(currentPage);

		int listCount = bService.getListCount();

		System.out.println(listCount);

		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);

		ArrayList<Board> list = bService.selectList(pi);

		mv.addObject("list", list);
		mv.addObject("pi", pi);
		mv.setViewName("board/boardListView");

		return mv;
	}

	@RequestMapping("bdetail.do")
	public ModelAndView boardDetail(ModelAndView mv, int bId,

			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {

		Board b = bService.selectBoard(bId);
		if (b != null) {
			mv.addObject("b", b).addObject("currentPage", currentPage)
					.setViewName("board/boardDetailView");
		} else {
			mv.addObject("msg", "게시글 상세조회 실패").setViewName("common/errorPage");
		}
		return mv;
	}

	@RequestMapping("binsertView.do")
	public String boardInsertView() {
		return "board/boardInsertForm";
	}

	@RequestMapping("binsert.do")
	public String insertBoard(Board b, HttpServletRequest request,
			@RequestParam(name = "uploadFile", required = false) MultipartFile file) {
		if (!file.getOriginalFilename().equals("")) {
			// 서버에 업로드 진행 
			// saveFile메소드 : 저장하고자하는 file과 request를 전달해서 서버에 업로드 시키고 저장된 파일명을 반환해주는 메소드
			String renameFileName = saveFile(file, request);

			if (renameFileName != null) { // 파일이 잘 저장된 경우
				b.setOriginalFileName(file.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}

		int result = bService.insertBoard(b);

		if (result > 0) {
			return "redirect:blist.do";
		} else {
			return "common/errorPage";
		}
	}

	public String saveFile(MultipartFile file, HttpServletRequest request) {
		// 파일이 저장될 경로를 설정하자
		// 웹서버 contextPath를 불러와서 폴더의 경로를 가져온다.(webapp하위의 resources)
		// C:\springWordspace\springProject\src\main\webapp\resources
		String root = request.getSession().getServletContext().getRealPath("resources");

		// root 하위의 buploadFiles 폴더를 연결
		// \를 문자로 인식하기 위해서는 \\를 사용한다.
		// C:\springWordspace\springProject\src\main\webapp\resources\buploadFiles
		//		String savePath = root + "\\buploadFiles";
		String savePath = root + File.separator + "buploadFiles";

		File folder = new File(savePath); // savePath의 폴더를 불러온다.

		if (!folder.exists()) {
			// 폴더가 없으면 만들어라.
			folder.mkdirs();
		}

		// 원본 파일명을 년원일시분초.파일확장자명으로 변경
		String originalFileName = file.getOriginalFilename(); // test.png

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//                       [       20200929191422.                                       ]
		String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
		//                       [       20200929191422.png                                       ]
				+ originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

		String renamePath = folder + File.separator + renameFileName; // 실제 저장될 파일 경로 + 파일명

		try {
			file.transferTo(new File(renamePath)); // 전달받은 file이 rename명으로 이때 서버에 저장이된다.
		} catch (Exception e) {
			System.out.println("파일 전송 에러 : " + e.getMessage());
		}
		return renameFileName;
	}

	@RequestMapping("bupView.do")
	public ModelAndView boardDetail(ModelAndView mv, int bId) {
		mv.addObject("b", bService.selectUpdateBoard(bId)).setViewName("board/boardUpdateForm");
		return mv;
	}

	@RequestMapping("bupdate.do")
	public ModelAndView boardUpdate(ModelAndView mv, Board b, HttpServletRequest request,
			@RequestParam(value = "reloadFile", required = false) MultipartFile file) {
		if (file != null && !file.isEmpty()) { // 새로 업로드한 파일이 있다면 
			if (b.getRenameFileName() != null) {
				deleteFile(b.getRenameFileName(), request);
			}
			String renameFileName = saveFile(file, request);
			if (renameFileName != null) {
				b.setOriginalFileName(file.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		int result = bService.updateBoard(b);

		if (result > 0) {
			mv.addObject("bId", b.getbId()).setViewName("redirect:bdetail.do");
		} else {
			mv.addObject("msg", "수정실패!").setViewName("common/errorPage");
		}
		return mv;
	}

	public void deleteFile(String fileName, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + File.pathSeparator + "buploadFiles";
		File f = new File(savePath + File.pathSeparator + fileName);
		if (f.exists()) {
			f.delete();
		}
	}

	@RequestMapping("bdelete.do")
	public String boardDelete(int bId, HttpServletRequest request) {
		Board b = bService.selectUpdateBoard(bId);
		if (b.getRenameFileName() != null) {
			deleteFile(b.getRenameFileName(), request);
		}
		int result = bService.deleteBoard(bId);
		if (result > 0) {
			return "redirect:blist.do";
		} else {
			return "common/errorPage";
		}
	}

	/**
	 * 1. stream을 이용해서 json배열 보내기 
	 * @param response
	 * @throws IOException 
	 */
//	@RequestMapping("topList.do")
//	public void boardTopList(HttpServletResponse response) throws IOException {
//		response.setContentType("application/json; charset=utf-8");
//		
//		ArrayList<Board> list = bService.selectTopList();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		JSONArray jArr = new JSONArray();
//		for (Board b : list) {
//			JSONObject jObj = new JSONObject();
//			jObj.put("bId",b.getbId());
//			jObj.put("bTitle",b.getbTitle());
//			jObj.put("bWiter",b.getbWriter());
//			jObj.put("originalFileName",b.getOriginalFileName());
//			jObj.put("bCount",b.getbCount());
//			jObj.put("bCreateDate",sdf.format(b.getbCreateDate()));
//			
//			jArr.add(jObj);
//		}
//		PrintWriter out = response.getWriter();
//		out.print(jArr);
//	}
	/**
	 * 2. Gson(Google + json)을 이용해서 
	 * @param response
	 * @throws IOException 
	 * @throws JsonIOException 
	 */
//	@RequestMapping("topList.do")
//	public void boardTopList(HttpServletResponse response) throws JsonIOException, IOException {
//		response.setContentType("application/json; charset=utf-8");
//		
//		ArrayList<Board> list = bService.selectTopList();
//		
//		// Gson도 날짜에 대해서는 날짜포맷을 변경시켜줘야한다.
//		// Gson객체의 속성값을 변경하고자 하면 GsonBuilder()를 통해서 변경을 한다.
//		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		gson.toJson(list,response.getWriter());
//	}
//	
	/*
	 * 3. jackson objectMapper를 이용하는 방식
	 *    jackson은 java Json라이브러리이고, jackson라이브러리는 objectMapper, JsonGenerator에 의존한다.
	 * 
	 * 	  * jackson 방법
	 * 	1. @ResponseBody 붙이고 반환값을 String으로 두고 objectMapper를 이용해서 list를 string올 반환하는 방법
	 *  2. 반환값을 list로 두고 list 자체를 반환하는 방법
	 *  	--> xml에 MessageConverter관련 bean을 등록해서 사용해야한다. (자바 객체를 자바스크립트객체로 바꿔주는 작업을 도와준다.)
	 * */
	/**
	 * 3. jackson을 이용한 방법 
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping(value = "topList.do",produces = "application/json; charset=utf-8")
	public String boardTopList() throws JsonProcessingException {
		ArrayList<Board> list = bService.selectTopList();
		
		ObjectMapper mapper = new ObjectMapper();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(sdf);
		
		String jsonStr = mapper.writeValueAsString(list);
		return jsonStr;
		
	}
	/**
	 * 게시글 댓글 목록 조회 
	 * @param response
	 * @param bId
	 * @throws IOException 
	 * @throws JsonIOException 
	 */
	@RequestMapping("rList.do")
	public void getReplyList(HttpServletResponse response, int bId) throws JsonIOException, IOException {
		ArrayList<Reply> rList = bService.selectReplyList(bId);
		
		response.setContentType("application/json; charset=utf-8"); 
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(rList,response.getWriter());
	}
	@ResponseBody
	@RequestMapping("addReply.do")
	public String addReply(Reply r) {
		int result = bService.insertReply(r);
		
		if (result >0) {
			return "success";
		} else {
			return"fail";
		}
	}
}
