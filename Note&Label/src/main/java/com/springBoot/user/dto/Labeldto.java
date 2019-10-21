package com.springBoot.user.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Labeldto {
	@NotEmpty
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
