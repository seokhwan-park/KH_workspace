package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.board.model.vo.Reply;

public interface BoardService {

	// 1. 게시판 전체 리스트 조회
	
	
	
	/**
	 * 1_1. 게시판 총 갯수 조회
	 * @return
	 * 
	 */
	int getListCount();
	
	
	/**
	 * 1_2. 게시판 리스트 조회
	 * @param pi
	 * @return
	 */
	ArrayList<Board> selectList(PageInfo pi);
	
	
	
	/**
	 * 2. 게시판 상세조회
	 * @param bId
	 * @return
	 */
	Board selectBoard(int bId);


	
	/**
	 * 3. 게시팡 작성
	 * @param b
	 * @return
	 */
	int insertBoard(Board b);

	
	
	/**
	 * 4. 게시판 수정
	 * @param bId
	 * @return
	 */
	Board selectUpdateBoard(int bId);
	
	
	/**
	 * 
	 * @param b
	 * @return
	 */
	int updateBoard(Board b);


	/**
	 * 5. 게시판 삭제
	 * @param bId
	 * @return
	 */
	int deleteBoard(int bId);


	/**
	 * 6. 게시글 TOP5 조회
	 * @return
	 */
	ArrayList<Board> selectTopList();


	
	
	/**
	 * 7. 댓글 목록 조회
	 * @param bId
	 * @return
	 */
	ArrayList<Reply> selectReplyList(int bId);


	
	/**
	 * 8. 댓글 등록
	 * @param r
	 * @return
	 */
	int insertReply(Reply r);

	
}




















