package com.springBoot.user.dto;

import lombok.Data;

@Data
public class Maildto 
{
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
