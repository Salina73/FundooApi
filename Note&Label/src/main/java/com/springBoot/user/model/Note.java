package com.springBoot.user.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note implements Serializable 
{
  
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteid;
	
	private Long userid;
	@NotEmpty
    private String title;
	@NotEmpty
    private String description;
	
	private String color;
	
	private boolean pinStatus;
	
	private boolean archiveStatus;
	
	private boolean trashStatus;
	
	private String noteImages; 
	
	private LocalDateTime time;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Label> label;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private List<Collaborator> userColaborators;

	public List<Collaborator> getUserColaborators() {
		return userColaborators;
	}
	public void setUserColaborators(List<Collaborator> userColaborators) {
		this.userColaborators = userColaborators;
	}
	public List<Label> getLabel() {
		return label;
	}
	public void setLabel(List<Label> label) {
		this.label = label;
	}
    
	public Long getNoteid() {
		return noteid;
	}
	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}
	public Long getUserid() {
		return userid;
	}
	
	public void setUserid(Long userid) {
		this.userid = userid;
	}
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public boolean isPinStatus() {
		return pinStatus;
	}
	public void setPinStatus(boolean pinStatus) {
		this.pinStatus = pinStatus;
	}
	public boolean isArchiveStatus() {
		return archiveStatus;
	}
	public void setArchiveStatus(boolean archiveStatus) {
		this.archiveStatus = archiveStatus;
	}
	public boolean isTrashStatus() {
		return trashStatus;
	}
	public void setTrashStatus(boolean trashStatus) {
		this.trashStatus = trashStatus;
	}
	public String getNoteImages() {
		return noteImages;
	}
	public void setNoteImages(String noteImages) {
		this.noteImages = noteImages;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}