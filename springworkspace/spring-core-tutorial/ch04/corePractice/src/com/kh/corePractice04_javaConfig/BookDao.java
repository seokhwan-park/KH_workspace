package com.kh.corePractice04_javaConfig;

import java.util.List;

public interface BookDao {

	// 도서 목록 전체 조회
	List<Book> selectBookList();
	
	// 도서 번호로 도서 조회
	Book selectOneBook(int bookSequence);
}
