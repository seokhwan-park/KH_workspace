package com.kh.corePractice02_xmlConfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


// class가 가지고 있는 타입을 생각
// 상세히 보여주려고 할 때
// 클래스의 타입을 지정! 

//@Component --> 아래 내용들을 통틀어 Component로 다 읽어온다.
//@Repository --> memberDao
//@Controller
//@Service

//@Component
@Component("myName")
public class MemberDaoImpl implements MemberDao {

	private final Map<Integer, Member> memberMap = new HashMap<>();
	
	public MemberDaoImpl() {
		memberMap.put(1, new Member(1,"user01","pass01","홍길동"));
		memberMap.put(2, new Member(2,"user02","pass02","유관순"));
	}
	
	
	// 매개변수로 전달받은 회원 번호를 map에서 조회 후 리턴해주는 용도
	@Override
	public Member selectMember(int memberSequence) {
		return memberMap.get(memberSequence);
	}

	
	// 매개변수로 전달받은 회원 정보를 map에 추가하고 성공/실패 여부를 boolean으로 리턴
	@Override
	public boolean insertMember(Member insertMember) {
		
		int before = memberMap.size();
		
		memberMap.put(insertMember.getMemberSequence(), insertMember);
		int after = memberMap.size();
		
		return (after > before)? true:false;
	}

}
