package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class AroundAspect {
	
	// 전처리, 후처리를 모두 해결하고자 할 때 사용하는 어드바이스
	
	private Logger logger = LoggerFactory.getLogger(AroundAspect.class);
	
	// around의 경우는 JoinPoint의 후손인 ProceedingJoinPoint를 파라미터로 선언
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Signature sign = joinPoint.getSignature();	// 메소드가 실행되는 실행 시점에 대한 신호를 찾아온다.
		
		// 풀 클래스명 ( 패키지 + 클래스 )
		String type = sign.getDeclaringTypeName();
		
		// 메소드 조회
		String method = sign.getName();
		
		String compName="";
		
		if(type.contains("Controller")) {
			compName += "Controller\t : " ;
		}else if(type.contains("Service")) {
			compName += "ServiceImpl\t : " ;
		}else if(type.contains("Dao")) {
			compName += "Dao\t\t : " ;
		}
		
		logger.debug("[Before]" + compName + type + "," + method+"()");
		
		// before부터 after까지의 수행시간을 체크할 때 사용
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(); 	// 시간 체크 시작
		
		Object obj = joinPoint.proceed();
		
		stopWatch.stop();	// 시간 체크 종료
		
		logger.debug("[After]" + compName + type + "," + method+"()"
				+ "수행에 걸린 시간" + stopWatch.getTotalTimeMillis() + "(ms)");
		
		return obj;	 // 원래의 흐름을 리턴
	}
}
