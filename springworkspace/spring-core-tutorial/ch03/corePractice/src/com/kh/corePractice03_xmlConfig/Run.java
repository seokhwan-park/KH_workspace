package com.kh.corePractice03_xmlConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Run {
	public static void main(String[] args) {
		ApplicationContext context = 
				new GenericXmlApplicationContext("com/kh/corePractice03_xmlConfig/configuration/appConfig.xml");
	
	
		Member member = context.getBean(Member.class);
		
		// appConfig.xml에서 등록한 bean객체를 가져온다.(Account가 구현체 타입을 가리키게 만들고 account.class하면 자동으로 따라간다.)
		Account acc = context.getBean(Account.class);
		
		System.out.println(member);
		System.out.println(acc);
		
		
	
	}
}
