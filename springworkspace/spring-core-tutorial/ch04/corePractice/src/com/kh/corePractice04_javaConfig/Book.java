package com.kh.corePractice04_javaConfig;

import java.util.Date;

public class Book {
	
	private int bookSequence;
	private int isbn;
	private String title;
	private String author;
	private String publisher;
	private Date createDate;
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(int bookSequence, int isbn, String title, String author, String publisher, Date createDate) {
		super();
		this.bookSequence = bookSequence;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.createDate = createDate;
	}

	public int getBookSequence() {
		return bookSequence;
	}

	public void setBookSequence(int bookSequence) {
		this.bookSequence = bookSequence;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Book [bookSequence=" + bookSequence + ", isbn=" + isbn + ", title=" + title + ", author=" + author
				+ ", publisher=" + publisher + ", createDate=" + createDate + "]";
	}
	
	
	
	
	
}
