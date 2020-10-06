package com.kh.corePractice04_javaConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Run {
	public static void main(String[] args) {
		ApplicationContext context = 
				new AnnotationConfigApplicationContext("com.kh.corePractice04_javaConfig");
		
		
		BookService bookService = context.getBean("bookService",BookService.class);
		
		for(Book b : bookService.selectAllBooks() ) {
			System.out.println(b);
		}
	}

	
}
