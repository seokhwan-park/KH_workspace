package com.kh.corePractice03_javaConfig.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.corePractice03_javaConfig.Account;
import com.kh.corePractice03_javaConfig.Member;
import com.kh.corePractice03_javaConfig.PersonalAccount;


// 이 파일이 스프링 설정파일임을 알려준다.
@Configuration
public class MemberConfiguration {
	
	
	// bean을 등록시켜주어야 한다.
	@Bean
	public Account accountGenerator() {
		return new PersonalAccount(20, "110-123-5678", "1234");
	}
	
	
	@Bean
	public Member memberGenerator() {
		
		// 생성자를 통하나 의존성 부여
		//return new Member(1, "홍길동", "010-1234-5785","hong123@gmail.com",accountGenerator());
		// accountGenerator가 account타입이지만 다형성에 의해 personalAccount타입으로 받을 수 있다.
		
		
		
		// setter를 이용한 의존성 부여
		// 생성자를 통해 Member객체 생성한것을 setter를 사용하는 방법으로 교체해보자.
		Member member = new Member();
		Account account = accountGenerator();
		member.setMemberSequence(1);
		member.setMemberName("홍길동");
		member.setPhone("010-2113-3678");
		member.setEmail("hong@gmail.com");
		member.setPersonalAccount(account);
		
		return member;
	}
	
	
}
