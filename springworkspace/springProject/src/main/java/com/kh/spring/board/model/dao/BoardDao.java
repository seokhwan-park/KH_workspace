package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

@Repository("bDao")
public class BoardDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int getListCount() {
		return sqlSession.selectOne("boardMapper.getListCount");
	}

	
	public ArrayList<Board> selectList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset,pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectList",null,rowBounds);	// 매개변수로 따로 필요한것이 없다!
	}

	
	public int updateCount(int bId) {
		
		return sqlSession.update("boardMapper.updateCount",bId);
	}


	public Board selectBoard(int bId) {

		return sqlSession.selectOne("boardMapper.selectBoard",bId);
	}


	public int insertBoard(Board b) {

		return sqlSession.insert("boardMapper.insertBoard",b);
	}


	public int updateBoard(Board b) {
		
		return sqlSession.update("boardMapper.updateBoard", b);
	}


	public int deleteBoard(int bId) {

		return sqlSession.delete("boardMapper.deleteBoard", bId);
	}

}
