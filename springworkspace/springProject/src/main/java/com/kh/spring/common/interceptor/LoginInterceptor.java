package com.kh.spring.common.interceptor;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.member.model.vo.Member;

/*
 * 	* Interceptor
 * 
 * 	- HandlerInterceptor -> HandlerInterceptor 
 * 
 * 	1. preHandle(전처리) : DispatcherServlet이 컨트롤러를 호출하기 전 작업
 *  2. postHandler(후처리) : 컨트롤러에서 DispatcherServlet으로 리턴되는 순간
 * 	3. afterCompletion(뷰단처리후) : 모든 뷰에서 최종결과를 생성하는 일을 포함한 모든 작업이 모두 완료된 후
 * 								   요청처리 중에 사용한 리소스를 반환해주기에 적당한 메소드
 * 
 */

public class LoginInterceptor extends HandlerInterceptorAdapter{
	// 로그인 요청 후 처리
	// 성공적으로 로그인 되었을 경우 로그인 이력을 남기게끔
	 private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HttpSession session = request.getSession();
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser != null) {
			InetAddress local = InetAddress.getLocalHost();
			
			logger.info(loginUser.getId() + " " + local.getHostAddress());
		}
		
	}
	 
	 
}
