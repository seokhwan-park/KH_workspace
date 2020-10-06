package com.kh.corePractice01_xmlConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Run {
	public static void main(String[] args) {
		ApplicationContext context = 
				new GenericXmlApplicationContext("com/kh/corePractice01_xmlConfig/appConfig.xml");
		
		Member member = (Member)context.getBean("member");	// object 타입으로 반환을 해준다.
		System.out.println(member);
		member.setMemberSequence(2);
		
		Member member2 = context.getBean("member", Member.class);
		System.out.println(member2);
		
		Member member3 = context.getBean(Member.class);		// 해당 타입으로 반환하겠다.
		System.out.println(member3);
		
		// 해쉬코드가 똑같다는 것은 동일한 객체를 바라보고 있다는 뜻이다.
		System.out.println(member.hashCode());
		System.out.println(member2.hashCode());
		System.out.println(member3.hashCode());
		
	}
}
