package com.kh.spring.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes("loginUser")
@Controller
public class MemberController {
	
	@Autowired
	private MemberService mService;
	
	// 암호화 처리 
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	// logging
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	
	
	// [ 파라미터를 전송 받는 방법]
	
	/*
	 * @RequestMapping 타입의 어노테이션을 붙여줌으로써 HandlerMapping 등록
	 * 
	 * @RequestMapping의 속성
	 * 여러개의 속성을 명시할 때는 "value="를 명시해야하고,
	 * value만 명시해야되는 경우는 @RequestMapping("login.do") 처럼 사용 가능
	 */
	
	// 1. HttpServletRequest를 통해 전송받기(기존 jsp/servlet 방식)
	//    메소드의 매개변수로 HttpServletRequest를 작성하면 메소드 실행 시
	//    스프링 컨테이너가 자동으로 객체를 인자로 주입해준다.
//	@RequestMapping(value="login.do",method=RequestMethod.POST)
//	public String memberLogin(HttpServletRequest request) {
//		String id= request.getParameter("id");
//		String pwd= request.getParameter("pwd");
//		
//		System.out.println("Id : " + id);
//		System.out.println("PWD : " + pwd);
//		return "home";
//	}
	
	/* 
	 *  2. @RequestParam 어노테이션 방식
	 * 
	 *  파라미터 value 속성에 없는 값이 넘어오는 경우 "" 이기 때문에 에러는 안남
	 *  @RequestParam(value="currentPage",required=false,defaultValue="1")
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST)
//	public String memberLogin(@RequestParam("id") String id,
//							  @RequestParam("pwd") String pwd
//			) {
//		System.out.println("Id : " + id);
//		System.out.println("PWD : " + pwd);
//		return "home";
//	}
	
	/*
	 * 3. @RequestParam 어노테이션 생략
	 *  
	 *  위의 어노테이션을 생략해도 파라미터 값을 가져와서 변수에 저장할 수 있다.
	 *  (단, 매개변수를 name값과 동일하게 해야 자동으로 값이 주입된다.)
	 *  
	 *  해당 어노테이션을 생략할 경우 defaultValue와 required 설정 불가
	 *  없는 파라미터일 경우 null값이 입력
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST)
//	public String memberLogin(String id, String pwd) {
//		System.out.println("Id : " + id);
//		System.out.println("PWD : " + pwd);
//		return "home";
//	}
	
	//=======================================================/
	
	/*
	 * 4. @ModelAttribute를 이용한 값 전달 방법
	 * 
	 *  요청 파라미터가 많은 경우 객체 타입으로 넘겨 받게 된다.
	 *  --> 기본생성자와 setter를 이용한 주입 방식이기 떄문에 둘중 하나라도 없으면 에러 발생
	 *      기본생성자 없으면 --> 생성자가 없다는 에러 발생
	 *      해당 필드 관련 setter 메소드가 없으면 null로 출력
	 *  
	 *  이름 커맨드 객체 방식
	 *  스프링 컨테이너가 기본생성자를 통해 Member객체를 생성하고
	 *  setter메소드로 꺼낸 파라미터들로 값을 변경한 후에
	 *  현재 이 메소드를 호출할 때 인자로 전달하며 호출하는 방식으로 주입한다.
	 *  (주의 사항: 반드시[ name속성의 값과 필드명이 동일해야한다. ] , setter 작성하는 규칙에 맞게 작성)
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST) 
//	public String memberLogin(@ModelAttribute Member m) {          
//		System.out.println("Id : " + m.getId());                       
//		System.out.println("PWD : " + m.getPwd());                     
//		return "home";                                          
//	}
	
	/*
	 * 위의 @ModelAttribute 어노테이션 생략하고 객체로 바로 전달받는 방법
	 *  
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST) 
//	public String memberLogin(Member m, HttpSession session) {          
//		
//		// 로그인  처리
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser", loginUser);
//		}else {
//			return "common/errorPage";
//		}
//		
//		return "redirect:home.do";                                          
//	}
	
	/*
	 * 1. Model 객체를 사용하는 방법
	 * 
	 *  커맨드 객체로 Model을 사용하게 되면 뷰(view)로 전달하고자하는 데이터를 맵형식(key,value)로 담을 때 사용한다.
	 *  scope는 request이다.
	 * 
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST) 
//	public String memberLogin(Member m,Model model ,HttpSession session) {          
//		
//		// 로그인  처리
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser", loginUser);
//		}else {
//			model.addAttribute("msg", "로그인실패!");
//			return "common/errorPage";
//		}
//		
//		return "redirect:home.do";                                          
//	}
	
	/*
	 * 2. ModelAndView 객체를 사용하는 방법
	 *    Model은 전달하고자 하는 데이터를 맵형식으로 담는 공간
	 *    View는 requestDispatcher처럼 forward 할 뷰 페이지 정보를 담고있는 객체 
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST) 
//	public ModelAndView memberLogin(Member m,ModelAndView mv ,HttpSession session) {          
//		
//		// 로그인  처리
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			session.setAttribute("loginUser", loginUser);
//			mv.setViewName("redirect:home.do");                                          
//		}else {
//			mv.addObject("msg", "로그인실패!");
//			mv.setViewName("common/errorPage");
//		}
//		return mv;
//	}	
	
	/**
	 * 로그아웃 용 컨트롤러
	 * @param session
	 * @return
	 */
//	@RequestMapping("logout.do")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:home.do";
//	}
	
	/*
	 * 3. session에 저장할 때 @SessionAttributes 사용하기
	 * 
	 *    Model에 Attribute가 추가될 때 자동으로 키값을 찾아 세션에 등록하는 기능을 제공하는 어노테이션
	 * 
	 */
//	@RequestMapping(value="login.do",method=RequestMethod.POST) 
//	public String memberLogin(Member m,Model model) {          
//		
//		// 로그인  처리
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser != null) {
//			model.addAttribute("loginUser", loginUser);
//			return "redirect:home.do";
//		}else {
//			model.addAttribute("msg", "로그인실패!");
//			return "common/errorPage";
//		}
//	}
	
	// 로그아웃 컨트롤러 2
	@RequestMapping("logout.do")
	public String logout(SessionStatus status) {
		
		status.setComplete(); 
		return "redirect:home.do";
	}
	
	@RequestMapping(value="login.do",method=RequestMethod.POST) 
	public String memberLogin(Member m,Model model) {          
//		logger.debug("memberLogin 호출됨"+m);
		if (logger.isDebugEnabled()) { // logger속성에 level value="debug"일때 실행한다.
			logger.debug("memberLogin 호출됨"+m);
		}
		
		// 로그인  처리
		Member loginUser = mService.loginMember(m);
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getPwd(), loginUser.getPwd())) {
			model.addAttribute("loginUser", loginUser);
			return "redirect:home.do";
		}else {
			model.addAttribute("msg", "로그인실패!");
			return "common/errorPage";
		}
	}
	
	
	//=============  회원 가입 ==================
	@RequestMapping("enrollView.do")
	public String enrollView() {
		return "member/memberInsertForm";
	}
	
	@RequestMapping("minsert.do")
	public String insertMember(Member m, Model model, 
							   @RequestParam("post") String post,
							   @RequestParam("address1") String address1,
							   @RequestParam("address2") String address2) {
		System.out.println(m);
		System.out.println(post +"," + address1+","+address2);
		
		if(!post.equals("")) {
			m.setAddress(post +"," + address1+","+address2);
		}
		
		System.out.println(bcryptPasswordEncoder.encode(m.getPwd()));
		
		String encPwd = bcryptPasswordEncoder.encode(m.getPwd());
		m.setPwd(encPwd);
		
		int result = mService.insertMember(m);
		
		if(result > 0) {
			return "redirect:home.do";
		}else {
			model.addAttribute("msg", "회원 가입에 실패하였습니다.!");
			return "common/errorPage";
		}
		
	}
	
	// 뷰 페이지로 이동하는 컨트롤러
	@RequestMapping("myInfo.do")
	public String myInfoView() {
		return "member/myPage";
	}
	
	// 회원 정보 수정
	@RequestMapping("mupdate.do")
	public String memberUpdate(Member m, Model model,
							   @RequestParam("post") String post,
							   @RequestParam("address1") String addr1,
							   @RequestParam("address2") String addr2) {
		if(!post.equals("")) {
			m.setAddress(post +"," + addr1 + "," +addr2);
		}
		
		int result = mService.updateMember(m);
		
		if(result > 0) {
			model.addAttribute("loginUser", m);
			return "redirect:home.do";
		}else {
			model.addAttribute("msg", "회원 정보 수정 실패!");
			return "common/errorPage";
		}
	}

	
	// 회원 탈퇴 
	@RequestMapping("mdelete.do")
	public String memberDelete(String id,Model model) {
		
		int result = mService.deleteMember(id);
		
		if(result > 0) {
//			status.setComplete();
//			return "redirect:home.do";
			return "redirect:logout.do";
		}else {
			model.addAttribute("msg", "회원 탈퇴 실패");
			return "common/errorPage";
		}
	}
	
//	@RequestMapping("idCheck.do")
//	public void idCheck(String id, HttpServletResponse response) throws IOException {
//		int result = mService.idCheck(id);
//		
//		PrintWriter out = response.getWriter();
//		if (result > 0) {
//			out.print("fail");
//		}else {
//			out.print("ok");
//		}
//		System.out.println(result);
//	}
	
	/**
	 * @ResponseBody를 이용해서 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("idCheck.do")
	public String idCheck(String id) {
		int result = mService.idCheck(id);
		
		if (result > 0) {
			return "fail";
		}else {
			return "ok";
		}
	}
}





