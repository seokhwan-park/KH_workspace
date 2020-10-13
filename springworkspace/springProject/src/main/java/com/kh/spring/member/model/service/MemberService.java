package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	/**
	 * 1. 회원 로그인 서비스를 위한 메소드
	 * @param m
	 * @return Member
	 */
	Member loginMember(Member m);
	
	/**
	 * 2. 회원 가입 서비스를 위한 메소드
	 * @param m 
	 * @return 성공시 1이상/ 실패시 -1
	 */
	int insertMember(Member m);
	
	/**
	 * 3. 회원 정보 수정 서비스를 위한 메소드
	 * @param m
	 * @return
	 */
	int updateMember(Member m);
	
	/**
	 * 4. 회원 탈퇴 서비스를 위한 메소드
	 * @param id (로그인한 사용자)
	 * @return
	 */
	int deleteMember(String id);

	/**
	 * 5. 아이디 중복체크 
	 * @param id
	 * @return
	 */
	int idCheck(String id);
}














