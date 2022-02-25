package com.zensar.olx.bean;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;


@Embeddable
public class OlxUser {
	@Column
	private int olxUserId;
	@Transient//to be not stored in db
	private String firstName;
	@Transient//to be ignored by db
	private String lastName;
	@Transient
	private String userName;
	@Transient
	private Active active;
	@Transient
	private String password;
	@Transient
	private String emailId;
	@Transient
	private String phoneNo;
	@Transient
	private String roles;
	
	public OlxUser(int olxUserId, String firstName, String lastName, String userName, Active active, String password,
			String emailId, String phoneNo, String roles) {
		super();
		this.olxUserId = olxUserId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.active = active;
		this.password = password;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.roles = roles;
	}
	public OlxUser(String firstName, String lastName, String userName, Active active, String password, String emailId,
			String phoneNo, String roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.active = active;
		this.password = password;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.roles = roles;
	}
	public OlxUser() {
		super();
	}
	
	public OlxUser(int olxUserId) {
		super();
		this.olxUserId = olxUserId;
	}
	public int getOlxUserId() {
		return olxUserId;
	}
	public void setOlxUserId(int olxUserId) {
		this.olxUserId = olxUserId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Active getActive() {
		return active;
	}
	public void setActive(Active active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "OlxUser [olxUserId=" + olxUserId + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", active=" + active + ", password=" + password + ", emailId=" + emailId + ", phoneNo="
				+ phoneNo + ", roles=" + roles + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(active, emailId, firstName, lastName, olxUserId, password, phoneNo, roles, userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OlxUser))
			return false;
		OlxUser other = (OlxUser) obj;
		return active == other.active && Objects.equals(emailId, other.emailId)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& olxUserId == other.olxUserId && Objects.equals(password, other.password)
				&& Objects.equals(phoneNo, other.phoneNo) && Objects.equals(roles, other.roles)
				&& Objects.equals(userName, other.userName);
	}
}
