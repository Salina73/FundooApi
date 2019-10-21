package com.springBoot.user.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springBoot.response.Response;
import com.springBoot.user.dto.Collaboratordto;

import com.springBoot.user.dto.Labeldto;
import com.springBoot.user.dto.Notedto;
import com.springBoot.user.model.Collaborator;
import com.springBoot.user.model.Label;
import com.springBoot.user.model.Note;

@Service
public interface UserService 
{


	//CRUD for Note
	Response create(Notedto notedto, String token);

//	List<Note> showNotes(String token);

	Response updatenote(String token, @Valid Notedto notedto, Long noteid);

	Response deletenote(String token, Long noteid);
	
	List<Note> showLabelsById(String token, Long labelid);

	//CRUD for Label
	Response createLabel(String token, @Valid Labeldto labeldto);
	
//	List<Label> showLabels(String token);

	Response updateLabel(String token, Long labelid, @Valid Labeldto labeldto);
	
	Response deleteLabel(String token, Long labelid);

	Response labelForNote(String token, @Valid Labeldto labeldto, Long noteid);

	Response removeLabel(String token, Long labelid, Long noteid);

	Response labelToNote(String token, Long labelid, Long noteid);

	List<Label> showNotesById(String token, Long noteid);
	
	//Note operations

	Response colorToNote(String token, String color, Long noteid);

	Response trashNote(String token, Long noteid);

	Response archiveUnarhiveNote(String token, Long noteid);

	Response pinNote(String token, Long noteid);

	List<Note> showPinnedNotes(String token);

	List<Note> showArchiveNotes(String token);

	List<Note> showTrashNotes(String token);

	List<Note> showUnpinNotes(String token);

	List<Note> showUnarchiveNotes(String token);

	List<Note> showUntrashNotes(String token);

	//Profile Pic

	Response uploadImageToNote(String token, MultipartFile image, Long noteid) throws IOException;

	Resource noteImages(String token, Long noteid) throws MalformedURLException;

	//Reminder
	Response setReminder(String token, Long noteid, LocalDateTime time);

	Response discardReminder(String token, Long noteid);
	
	LocalDateTime viewReminder(String token, Long noteid);
	
	//Collaborator
	Response addCollaboratorsToNote(String token, Long noteid, Collaboratordto collabDto);
	
	Response removeCollaboratorFromNote(String token, Long noteid, Collaboratordto collabDto);

	List<Collaborator> collaboratorOfNote(String token, Long noteid);

//	List<Collaborator> collaboratorOfUser(String token);

	Response checkingReminder(String token, Long noteid);

//	List<String> showProfile();

	List<Label> showLabelsOfNote(String token, Long noteId);
		
}
