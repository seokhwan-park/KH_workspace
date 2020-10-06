package com.kh.corePractice01_javaConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Run {
	public static void main(String[] args) {
		
		// 스프링 IOC컨테이너 생성
		// 설정 클래스 정보를 가지고 ApplicationContext 객체 생성
		// 어노테이션 설정 정보를 읽어서 구현
		ApplicationContext context = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		
		// applicationConfig.java 안에서 설정해놓은 getMember
		Member member = (Member)context.getBean("getMember");
		System.out.println(member);
		
		Member member2 = context.getBean(Member.class);
		System.out.println(member2);
		
		
	}
}
