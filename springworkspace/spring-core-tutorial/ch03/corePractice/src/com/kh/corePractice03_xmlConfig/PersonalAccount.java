package com.kh.corePractice03_xmlConfig;

public class PersonalAccount implements Account {

	private int bankCode;
	private String accNo;
	private String accPwd;
	private int balance=0;
	
	
	
	
	public PersonalAccount(int bankCode, String accNo, String accPwd) {
		super();
		this.bankCode = bankCode;
		this.accNo = accNo;
		this.accPwd = accPwd;
	}

	public PersonalAccount(int bankCode, String accNo, String accPwd, int balance) {
		super();
		this.bankCode = bankCode;
		this.accNo = accNo;
		this.accPwd = accPwd;
		this.balance = balance;
	}

	
	// 현재 잔액을 출력해주는 메소드
	@Override
	public String getBalance() {
		
		return this.accNo + " 계좌의 현재 잔액은" + this.balance + " 원 입니다.";
	}

	
	// 금액을 매개변수로 받아 잔액을 증가시켜주는 메소드
	@Override
	public String deposit(int money) {
		
		String str = "";
		if(money>=0) {
			this.balance += money;
			str = money + "원이 입금되었습니다.";
		}else {
			str = "금액을 잘 못 입력하셨습니다.";
		}
		
		return str;
	}

	
	// 금액을 매개변수로 받아 잔액을 감소시켜주는 메소드
	@Override
	public String withDraw(int money) {
		String str = "";
		
		if(balance >= money) {
			this.balance -= money;
			str = money + "원이 출금되었습니다.";
		}else {
			str = "잔액이 부족합니다. 잔액을 확인해주세요";
		}
		
		return str;
	}

}
