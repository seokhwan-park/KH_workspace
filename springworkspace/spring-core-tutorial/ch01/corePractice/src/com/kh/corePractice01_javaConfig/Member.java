package com.kh.corePractice01_javaConfig;

public class Member {
	private int memberSequence;
	private String memberId;
	private String memberPwd;
	private String memberName;
	
	public Member() {

	}
	
	
	public Member(int memberSequence, String memberId, String memberPwd, String memberName) {
		super();
		this.memberSequence = memberSequence;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
	}
	
	public int getMemberSequence() {
		return memberSequence;
	}
	
	public void setMemberSequence(int memberSequence) {
		this.memberSequence = memberSequence;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getMemberPwd() {
		return memberPwd;
	}
	
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	
	public String getMemberName() {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Member [memberSequence=" + memberSequence + ", memberId=" + memberId + ", memberPwd=" + memberPwd
				+ ", memberName=" + memberName + "]";
	}
	
	
	
	
	
}
