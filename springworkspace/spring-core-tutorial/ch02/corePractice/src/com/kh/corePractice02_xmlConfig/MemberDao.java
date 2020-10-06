package com.kh.corePractice02_xmlConfig;

public interface MemberDao {
	
	// 회원정보로 회원정보를 조회하는 메소드
	Member selectMember(int memberSequence);
	
	
	
	// 회원정보를 저장하고 결과를 리턴하는 메소드
	boolean insertMember(Member insertMember);
}
