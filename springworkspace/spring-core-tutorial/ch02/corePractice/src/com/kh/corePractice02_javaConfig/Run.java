package com.kh.corePractice02_javaConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kh.corePractice02_javaConfig.configuration.MemberConfiguration;

public class Run {
	public static void main(String[] args) {
		ApplicationContext context = 
//				new AnnotationConfigApplicationContext("com.kh.corePractice02_javaConfig");
		// basePackage 설정
		
				new AnnotationConfigApplicationContext(MemberConfiguration.class);
		
//		MemberDao memberDao = context.getBean(MemberDao.class);
		MemberDao memberDao = (MemberDao)context.getBean("myName");
		System.out.println(memberDao.selectMember(1));
		System.out.println(memberDao.insertMember(new Member(3, "user03", "pass03", "새로운멤버")));
		System.out.println(memberDao.selectMember(3));
		
	}
}
