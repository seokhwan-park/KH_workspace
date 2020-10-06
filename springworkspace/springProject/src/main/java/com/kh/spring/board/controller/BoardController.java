package com.kh.spring.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.common.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
	@RequestMapping("blist.do")
	public ModelAndView boardList(ModelAndView mv,
								@RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) { // required를 false를 주면 필수값은 아니라는 뜻
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
									@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage) {
		
		
		Board b = bService.selectBoard(bId);
		
		if(b != null) {
			mv.addObject("b",b).addObject("currentPage", currentPage).setViewName("board/boardDetailView");
		}else {
			mv.addObject("msg","게시글 상세조회 실패").setViewName("common/errorPage");
		}
		

		return mv;
	}
	
	
	@RequestMapping("binsertView.do")
	public String boardInsert() {
		return "board/boardInsertForm";
	}
	
	
	@RequestMapping("binsert.do")	// @ModelAttribute Board b에서 생략된것
	public String insertBoard(Board b, HttpServletRequest request,
					@RequestParam(name="uploadFile",required=false)MultipartFile file) {
		if(!file.getOriginalFilename().equals("")) {
			// 서버에 업로드 진행
			// saveFile메소드 : 저장하고자하는 file과 request를 전달해서 서버에 업로드시키고 저장된 파일명을 변환해 주는 메소드
			String renameFileName = saveFile(file,request);
			
			if(renameFileName != null ) {	// 파일이 잘 저장된 경우
				b.setOriginalFileName(file.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		
		int result = bService.insertBoard(b);
		
		if(result > 0) {
			return "redirect:blist.do";
		}else {
			return "common/errorPage";
		}
	}
	
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		// 파일이 저장될 경로를 설정하자
		// 웹 서버 contextPath를 불러와서 폴더의 경로를 가져온다.(webapp하위의 resources)
		// 현재 나의 프로젝트의 resources의 실제 경로를 찾아오겠다. --> C:\Users\yuiii\OneDrive\KH Workspace\springworkspace\springProject\src\main\webapp\resources
		// 사용자가 달라질때마다 경로가 다를 수 있기 때문에 각각의 경로를 뽑아온다.
		String root = request.getSession().getServletContext().getRealPath("resources");
		
		// root 하위의 buploadFiles 폴더를 연결
		// \를 문자로 인식하기 위해서는 \\를 사용한다.
		String savePath = root + "\\buploadFiles";	// ->C:\Users\yuiii\OneDrive\KH Workspace\springworkspace\springProject\src\main\webapp\resources\buploadFiles
		
		File folder = new File(savePath);	// java.io class사용 --> savePath의 폴더를 불러온다.
		
		if(!folder.exists()) {	// 해당 경로에 해당 폴더가 있는지 여부 확인 메소드!
			// 폴더가 없으면 만들어라!!
			folder.mkdirs();	// 폴더를 생성시켜주는 메소드!!
		}
		
		// 원본 파일명을 년월일시분초.파일확장자 명으로 변경
		String originalFileName = file.getOriginalFilename();	// test.png 처럼 들어가있는 파일명으로 가져온다!
		
		// date객체에 있는것을 우리가 원하는 형태로 뽑아낼 때 사용!
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//					  [			20200929191422.
		String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis())) + "."
								+ originalFileName.substring(originalFileName.lastIndexOf(".")+1);
		// .의 위치에 대한 index + 1까지를 잘라내고 확장자 부분만 가져온다!
		
	
		String renamePath = folder + "\\" + renameFileName;	// 실제 저장될 파일 경로 + 파일명 --> 폴더에 파일을 추가한다는 의미!!
		//					새로만든 폴더의 경로(스크린샷) -- file folder는 경로를 나타낸다.
		try {
			file.transferTo(new File(renamePath));	// 전달받은 file에 rename명으로 이때 서버에 저장된다.
		}catch(Exception e) {
			System.out.println("파일 전송 에러: " + e.getMessage());
		}
		
		return renameFileName;
	}
	
	
	@RequestMapping("bupView.do")
	public ModelAndView boardDetail(ModelAndView mv, int bId) {
		
		mv.addObject("b", bService.selectUpdateBoard(bId)).setViewName("board/boardUpdateForm");
		return mv;
		
	}
	
	
	@RequestMapping("bupdate.do")
	public ModelAndView boardUpdate(ModelAndView mv, Board b, 
				HttpServletRequest request,
				@RequestParam(value="reloadFile", required=false) MultipartFile file) {
		
		if(file != null && !file.isEmpty()) {	// 새로 업로드된 파일이 있다면 이미 업로드되어있던 파일은 찾아서 삭제해주어야 한다.
			if(b.getRenameFileName() != null) { // 기존에 업로드된 파일이 존재하는 경우 기존 파일 삭제
				deleteFile(b.getRenameFileName(), request);
			}
			
			String renameFileName = saveFile(file, request);
			
			if(renameFileName != null) {
				b.setOriginalFileName(file.getOriginalFilename());
				b.setRenameFileName(renameFileName);
			}
		}
		
		int result = bService.updateBoard(b);
		
		if (result > 0) {
			mv.addObject("bId",b.getbId()).setViewName("redirect:bdetail.do");
		}else {
			mv.addObject("msg","수정 실패").setViewName("common/errorPage");
		}
		
		return mv;
	}
			
	public void deleteFile(String fileName, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\buploadFiles";
		// C:\Users\yuiii\OneDrive\KH Workspace\springworkspace\springProject\src\main\webapp\resources\buploadFiles
		
		File f = new File(savePath + "\\" + fileName);
		
		if(f.exists()) {
			f.delete();
		}
	}
	
	@RequestMapping("bdelete.do")
	public String boardDelete(int bId, HttpServletRequest request) { // request : 파일을 찾아서 삭제시키기 위한 매개변수
		Board b = bService.selectUpdateBoard(bId);
		
		// renamefilename이 있다면 저장된 file이 있다는 뜻
		if(b.getRenameFileName() != null) {
			deleteFile(b.getRenameFileName(),request);
		}
		
		int result = bService.deleteBoard(bId);
		
		if(result > 0) {
			return "redirect:blist.do";
		}else {
			return "common/errorPage";
		}
	}
}






























