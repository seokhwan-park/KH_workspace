package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.member.model.vo.Member;

public class BlistInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = LoggerFactory.getLogger(BlistInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		HttpSession session = request.getSession();
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			logger.info("비로그인 상태에서 [" + request.getRequestURI() + "] 접근하려고 함 ");
			request.setAttribute("msg", "로그인 후 이용하세요.");
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
			
			return false; // false 반환을 해줘야 controller를 실행하지 않는다.
			
		}
		return true;
	}
	
	// 게시판 리스트 조회 요청 전 처리 
	
}