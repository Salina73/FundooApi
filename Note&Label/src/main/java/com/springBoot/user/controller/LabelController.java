package com.springBoot.user.controller;

import com.springBoot.exception.Exception;
import com.springBoot.user.dto.Labeldto;
import com.springBoot.user.dto.Notedto;
import com.springBoot.user.model.Label;
import com.springBoot.user.model.Note;
import com.springBoot.user.repository.LabelRepository;

import com.springBoot.response.Response;
import com.springBoot.user.service.UserService;
import com.springBoot.utility.TokenGeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class LabelController {

	@Autowired
	LabelRepository labelRepository;


	@Autowired
	UserService userService;

	@Autowired
	TokenGeneration token1;

	@Autowired
	Response statusResponse;

	Note updatedNote;

	// Create a new label
	@PostMapping("/createLabel")
	public ResponseEntity<Response> createLabel(@RequestHeader String token, @Valid @RequestBody Labeldto labeldto)
			throws Exception, UnsupportedEncodingException {
		Response response = userService.createLabel(token, labeldto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
//
//	// Get All label
//	@GetMapping("/getAllLabel")
//	public List<Label> getAllLabel(@RequestHeader String token) {
//		List<Label> allLabels = userService.showLabels(token);
//		return allLabels;
//
//	}
	//Get label of note
	@GetMapping("/getAllLabelOfNote")
	public List<Label> getAllLabelOfNote(@RequestHeader String token,@RequestParam Long noteId) {
		List<Label> allLabels = userService.showLabelsOfNote(token,noteId);
		return allLabels;

	}
	// Update a label
	@PutMapping("/updateLabel")
	public ResponseEntity<Response> updateLabel(@RequestHeader String token, @RequestParam Long labelid,
			 @RequestBody Labeldto labeldto) {
		Response response = userService.updateLabel(token, labelid, labeldto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Delete a label
	@DeleteMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLabel(@RequestHeader String token, @RequestParam Long labelid) {
		Response response = userService.deleteLabel(token, labelid);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/addLabelToNote")
	public ResponseEntity<Response> addLabelToNote(@RequestHeader String token, @Valid @RequestBody Labeldto labeldto,
			@RequestParam Long noteid) {
		Response response = userService.labelForNote(token, labeldto,noteid);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PostMapping("/appendLabelToNote")
	public ResponseEntity<Response> appendLabelToNote(@RequestHeader String token, @RequestParam Long labelid,
			@RequestParam Long noteid) {
		Response response = userService.labelToNote(token, labelid,noteid);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@DeleteMapping("/removeFromNote")
	public ResponseEntity<Response> removeFromNote(@RequestHeader String token, @RequestParam Long labelid,@RequestParam Long noteid)
	{
		Response response = userService.removeLabel(token, labelid,noteid);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/getNotesByLabelid")
	public List<Note> getNoteByLabelid(@RequestHeader String token,@RequestParam Long labelid) {
		List<Note> allLabels = userService.showLabelsById(token,labelid);
		return allLabels;

	}
	
}