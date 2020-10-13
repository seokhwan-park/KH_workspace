package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository("mDao")
public class MemberDao {

	@Autowired
	SqlSessionTemplate sqlSession; // root-context에서 작성한 bean으로 생성되어 주입된다.
	
	public Member loginMember(Member m) {
		return (Member)sqlSession.selectOne("memberMapper.loginMember",m);
	}

	public int insertMember(Member m) {
		return sqlSession.insert("memberMapper.insertMember",m);
	}

	public int updateMember(Member m) {
		return sqlSession.update("memberMapper.updateMember", m);
	}

	public int deleteMember(String id) {
		return sqlSession.update("memberMapper.deleteMember",id);
	}

	public int idCheck(String id) {
		return sqlSession.selectOne("memberMapper.idCheck",id);
	}
	
	// 트랜잭션 테스트용
	public int insertBoard() {
		return sqlSession.insert("memberMapper.insertBoard");
	}

}
