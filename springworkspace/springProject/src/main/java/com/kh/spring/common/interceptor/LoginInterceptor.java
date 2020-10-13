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
 * Interceptor
 * 
 * 정확이 얘기하자면 HandlerInterceptor -> (HandlerInterceptorAdapter를 상속해서 구현)
 * 
 * - 세가지의 메소드 오버라이딩 해서 사용
 * 
 * 1. preHandle(전처리) 			: DispatcherServlet이 컨트롤러를 호출하기 전
 * 2. postHandle(후처리)			: 컨트롤러에서 DispatcherServlet으로 리턴되는 순간 
 * 3. afterCompletion(뷰단처리후) 	: 모든 뷰에서 최종결과를 생성하는 일ㅇ르 포함한 모든작업이 완료된 후
 * 									요청처리 중에 사용한 리소스를 반환해주기에 적합한 메소드 
 * 
 * */

public class LoginInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser != null) {
			InetAddress local = InetAddress.getLocalHost();
			logger.info(loginUser.getId() + " " + local.getHostAddress());
			
			
		}
	}

}
