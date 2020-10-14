package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kh.spring.member.model.vo.Member;

@Component
@Aspect
public class AfterReturningAspect {
	
	private Logger logger = LoggerFactory.getLogger(AfterReturningAspect.class);
															
																// (..) : 매개변수의 개수와 타입에 제약이 없음
																// (*)  : 반드시 1개의 매개변수를 가지는 메소드만 선택
	@AfterReturning(pointcut="execution(* com.kh.spring..*Impl.login*(*))",returning="returnObj")
	public void loggerAdvice(JoinPoint jointPoint, Object returnObj) {
		
		if(returnObj instanceof Member) {
			Member m = (Member)returnObj;
			
			if(m.getId().equals("admin")) {
				logger.debug("[log] : 관리자님 환영합니다.");
			}else {
				logger.debug("[log] : " + m.getId() + " 로그인");
			}
		}
		
	}
}
