 package com.kh.corePractice02_javaConfig.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.kh.corePractice02_javaConfig.MemberDao;
import com.kh.corePractice02_javaConfig.MemberDaoImpl;


// @Configuration, @Bean, @Component, @Repository, @Service, @Controller가 
// 달린 클래스를 모두 감지


@Configuration
@ComponentScan(basePackages="com.kh.corePractice02_javaConfig",

		// 스캐닝에 포함할 내용을 작성 
		includeFilters = {
				@ComponentScan.Filter(
				type=FilterType.REGEX,
				pattern= {"com.kh.corePractice02_javaConfig.*Dao",
						  "com.kh.corePractice02_javaConfig.*Service"}
						)
		},
		
		// 스캐닝에 제외할 내용을 작성
		excludeFilters = {
				@ComponentScan.Filter(

						// 스프링에서 제공하는 @Component 어노테이션이 붙은 클래스는 스캐닝에서 제외하겠다.

//						type=FilterType.ANNOTATION,
//						classes= {org.springframework.stereotype.Component.class}
						
						
						type=FilterType.ASSIGNABLE_TYPE,
						classes= {MemberDao.class}
						
				)
				
		}
		


		
		)
public class MemberConfiguration {
	// exclude로 제외했기 때문에 MemberDao객체를 찾지 못한다.
	
	@Bean
	public MemberDao getMemberDao() {
		return new MemberDaoImpl();
	}
	

}

// 스프링이 지원하는 필터 타입은 네 종류
// Annotation : 필터 대상 어노테이션 타입을 지정해서 매칭하는 용도
// Assignable_type : 클래스/인터페이스를 지정하여 매칭하는 용도
// Regex : 정규표현식으로 클래스를 매치하는 용도
// Aspectj : 포인트컷 표현식으로 클래스를 매치하는 용도
