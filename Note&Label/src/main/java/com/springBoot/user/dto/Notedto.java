package com.springBoot.user.dto;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Notedto 
{
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
			
}
