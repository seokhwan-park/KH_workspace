package com.kh.corePractice03_javaConfig;

public class Member {

	private int memberSequence;
	private String memberName;
	private String phone;
	private String email;
	private Account personalAccount;
	
	
	public Member() {
		super();
	}


	public Member(int memberSequence, String memberName, String phone, String email, Account personalAccount) {
		super();
		this.memberSequence = memberSequence;
		this.memberName = memberName;
		this.phone = phone;
		this.email = email;
		this.personalAccount = personalAccount;
	}


	public int getMemberSequence() {
		return memberSequence;
	}


	public void setMemberSequence(int memberSequence) {
		this.memberSequence = memberSequence;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Account getPersonalAccount() {
		return personalAccount;
	}


	public void setPersonalAccount(Account personalAccount) {
		this.personalAccount = personalAccount;
	}


	@Override
	public String toString() {
		return "Member [memberSequence=" + memberSequence + ", memberName=" + memberName + ", phone=" + phone
				+ ", email=" + email + ", personalAccount=" + personalAccount + "]";
	}
	
	
}
