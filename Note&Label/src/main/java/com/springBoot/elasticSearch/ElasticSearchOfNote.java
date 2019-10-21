package com.springBoot.elasticSearch;

import java.io.IOException;
import java.util.List;

import com.springBoot.response.Response;
import com.springBoot.user.model.Note;

public interface ElasticSearchOfNote {
	Response createNote(Note note);

	Response updateNote(Note note, Long noteId);

	Response deleteNote(Long noteId);

	List<Note> searchNote(String token,String title) throws IOException;
}
