package com.springBoot.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springBoot.exception.Exception;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;
import com.springBoot.user.dto.Logindto;
import com.springBoot.user.dto.Maildto;
import com.springBoot.user.dto.Userdto;
import com.springBoot.user.model.User;
import com.springBoot.user.repository.UserRepo;
import com.springBoot.user.service.UserService;

@CrossOrigin( origins = "*")

@RestController
@RequestMapping("/user")
public class Controller 
{
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody Userdto userDto)
			throws Exception, UnsupportedEncodingException 
	{
		Response response = userService.register(userDto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody Logindto logindto)
			throws Exception, UnsupportedEncodingException
	{
		ResponseToken response = userService.login(logindto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/{token}")
	public ResponseEntity<Response> emailValidation(@PathVariable String token) throws Exception 
	{
		Response response = userService.validateEmailId(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody Maildto emailDto)
			throws UnsupportedEncodingException, Exception, MessagingException 
	{
		System.out.println(emailDto);
		Response status = userService.forgetPassword(emailDto);
		return new ResponseEntity<Response>(status, HttpStatus.OK);
	}
	
	@PutMapping("/setPassword")
    public ResponseEntity<Response> setPassword(@RequestBody Userdto userDto) throws Exception, UnsupportedEncodingException 
    {	
		Response response = userService.setpassword(userDto.getEmailId(),userDto.getPassword());
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

	@GetMapping("/getallusers")
	public List<User> getAllUsers()
	{
		return userRepo.findAll();
	}
	//remain
	@PostMapping(value="/uploadPic",consumes="multipart/form-data")
	public ResponseEntity<Response> uploadPic(@RequestHeader String token,MultipartFile image) throws IOException
	{
		Response response = userService.uploadProfilePic(token,image);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getProfile")
	public List<String> getProfile() throws MalformedURLException
	{
		List<String> user = userService.showProfile();
		return user;
	}
}
