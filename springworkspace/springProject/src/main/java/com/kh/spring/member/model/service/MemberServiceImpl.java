package com.kh.spring.member.model.service;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service("mService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao mDao;
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Override
	public Member loginMember(Member m) {
		Member loginUser = mDao.loginMember(m);
		return loginUser;
	}

//	@Override
//	public int insertMember(Member m) {
//		return mDao.insertMember(m);
//	}
	
	// 트랜잭션 처리
	// 1. 트랜잭션 기본값 설정 후 트랜잭션 상태를 관리하는 객체를 통해 트랜잭션을 처리하는 방법
//	@Override
//	public int insertMember(Member m) {
//		
//		// 트랜잭션에 대한 기본 세팅을 하기 위한 객체
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		
//		// 기본값에 대해 트랜잭션 행위를 설정
//		// TransactionDefinition.PROPAGATION_REQUIRED은 이미 하나의 트랜잭션이 존재한다면,
//		// 이후의 트랜잭션을 지원하고 없다면 새로운 트랜잭션을 시작한다는 의미
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		
//		// 설정한 트랜잭션 정의에 대한 객체를 트랜잭션 상태를 관리하는 객체를 생성해서 전달한다.
//		TransactionStatus status = transactionManager.getTransaction(def);
//		
//		// sqlSession내에서 Connection객체를 가져와서 AutoCommit을 false로 설정하겠다.
//		try{
//			sqlSession.getConnection().setAutoCommit(false);
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//		
//		// 비지니스 로직을 수행
//		// 두 개의 트랜잭션을 생성
//		int result1 = mDao.insertMember(m);
//		int result2 = mDao.insertBoard();
//		
//		int result = 0; // 위의 두 트랜잭션이 완료되면 강제적으로 트랜잭션 매니저를 통해 commit을 하겠다.
//		
//		if(result1 > 0 && result2 > 0) {
//			transactionManager.commit(status);
//			result = 1;
//		}else {
//			transactionManager.rollback(status);
//			result = 0;
//		}
//		
//		return result;
//	}
	
	// @Transactional 어노테이션 사용
//	@Override
//	@Transactional
//	public int insertMember(Member m) {
//		
//		int result = 0;
//		
//		int result1 = mDao.insertMember(m);
//		int result2 = mDao.insertBoard();
//		
//		if(result1 > 0 && result2 > 0) {
//			result = 1;
//		}
//		
//		return result;
//	}

	
	// AOP 설정하여 트랜잭션 적용
	@Override
	public int insertMember(Member m) {
		
		int result = 0;
		
		int result1 = mDao.insertMember(m);
		int result2 = mDao.insertBoard();
		
		if(result1 > 0 && result2 > 0) {
			result = 1;
		}
		
		return result;
	}
	
	
	@Override
	public int updateMember(Member m) {
		return mDao.updateMember(m);
	}

	@Override
	public int deleteMember(String id) {
		return mDao.deleteMember(id);
	}

	@Override
	public int idCheck(String id) {
		return mDao.idCheck(id);
	}



}
