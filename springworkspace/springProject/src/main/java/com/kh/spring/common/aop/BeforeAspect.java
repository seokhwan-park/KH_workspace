package com.kh.spring.common.aop;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controller, Service, Dao의 메소드들이 호출 될 떄마다 log찍히게 작업
public class BeforeAspect {
	
	private Logger logger = LoggerFactory.getLogger(BeforeAspect.class);
	
	public void loggerAdvice(JoinPoint joinpoint) {
		// 이 메소드가 실행되는 실행 시점에 대한 신호를 찾아오는 객체
		Signature sign = joinpoint.getSignature();
		
		// 실행되는 클래스의 풀명(패키지 + 클래스)을 알고 싶다면
		String type = sign.getDeclaringTypeName();
		
		// 어느 메소드에서 실행되는지 확인
		String method = sign.getName();	// 메소드명
		
		String compName="";
		
		if(type.contains("Controller")) {
			compName += "Controller\t : " ;
		}else if(type.contains("Service")) {
			compName += "ServiceImpl\t : " ;
		}else if(type.contains("Dao")) {
			compName += "Dao\t\t : " ;
		}
		
		logger.debug("[Before]" + compName + type + "," + method+"()");
		
	}

}
