package com.zensar.olx.bean;

public class LoginUser {

	private String userName;
	private String password;
	public LoginUser(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public LoginUser() {
		super();
	}
	public LoginUser(String userName) {
		super();
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginUser [userName=" + userName + ", password=" + password + "]";
	}
}
