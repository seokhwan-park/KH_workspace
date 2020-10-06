package com.kh.corePractice04_javaConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// 이기도 역시나 스프링 빈으로 등록
// 이름을 설정하지 않으면 클래스 이름의 앞글자를 소문자로 한 네이밍 규칙으로 빈이 생성된다.
@Component
public class BookService {
	
	// BookDao로 되어있는 bean객체를 연결시켜주는 것.
	// 필드 주입 -> @...("이름")과 다르게 필드에 주입시키는 것 즉, 이름이 bookDao..
	@Autowired
	private BookDao bookDao;
	// 다른 객체를 주입(의존성 부여)시켜준것!!
	
	public BookService() {}
	
	
	// 도서 정보 전체 조회용 메소드
	public List<Book> selectAllBooks(){
		return bookDao.selectBookList();
	}
	
	// 도서번호로 도서 검색용 메소드
	public Book searchBookSequence(int bookSequence) {
		return bookDao.selectOneBook(bookSequence);
	}
	
	
}
