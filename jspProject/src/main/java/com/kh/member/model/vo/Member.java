package com.kh.member.model.vo;

import java.sql.Date;

public class Member {
	
	private int userNo;
	private String userId;
	private String uderPwd;
	private String userName;
	private String phone;
	private String email;
	private String address;
	private String interest;
	private Date enrrollDate;
	private Date modifyDate;
	private String status;

	
	// 기본생성자, 매개변수 생성자
	
	public Member() {}
	

	public Member(String userId, String userName, String phone, String email, String address, String interest) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}




	public Member(int userNo, String userId, String uderPwd, String userName, String phone, String email,
			String address, String interest, Date enrrollDate, Date modifyDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.uderPwd = uderPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
		this.enrrollDate = enrrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}
	

	public Member(String userId, String uderPwd, String userName, String phone, String email, String address,
			String interest) {
		super();
		this.userId = userId;
		this.uderPwd = uderPwd;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.interest = interest;
	}




	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUderPwd() {
		return uderPwd;
	}


	public void setUderPwd(String uderPwd) {
		this.uderPwd = uderPwd;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getInterest() {
		return interest;
	}


	public void setInterest(String interest) {
		this.interest = interest;
	}


	public Date getEnrrollDate() {
		return enrrollDate;
	}


	public void setEnrrollDate(Date enrrollDate) {
		this.enrrollDate = enrrollDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", uderPwd=" + uderPwd + ", userName=" + userName
				+ ", phone=" + phone + ", email=" + email + ", address=" + address + ", interest=" + interest
				+ ", enrrollDate=" + enrrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}
	
	
	
}
