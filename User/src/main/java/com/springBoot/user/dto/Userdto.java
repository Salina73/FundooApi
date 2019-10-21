package com.springBoot.user.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Userdto 
{
	
	@NotEmpty(message = "Please enter firstName")
	private String firstName;
	@NotEmpty(message = "Please enter lastName")
	private String lastName;
	@NotEmpty(message = "Please enter emailId")
	private String emailId;
	@NotEmpty(message = "Please enter password")
	private String password;
	@NotEmpty(message = "Please enter mobileNumber")
	private String mobileNum;
	
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

}
