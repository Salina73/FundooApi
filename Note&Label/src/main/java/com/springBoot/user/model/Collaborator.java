package com.springBoot.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
@Entity
@Table
public class Collaborator 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collaboratorId;
	
	@NotEmpty
    private String email;
	
	private Long noteEntityId;
	private Long userEntityId;
	
	public Long getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(Long collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public Long getNoteEntityId() {
		return noteEntityId;
	}

	public void setNoteEntityId(Long noteEntityId) {
		this.noteEntityId = noteEntityId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserEntityId() {
		return userEntityId;
	}

	public void setUserEntityId(Long userEntityId) {
		this.userEntityId = userEntityId;
	}

	
	
}
