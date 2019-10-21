package com.springBoot.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springBoot.exception.Exception;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;

import com.springBoot.user.dto.Logindto;
import com.springBoot.user.dto.Maildto;

import com.springBoot.user.dto.Userdto;

import com.springBoot.user.model.User;
@Service
public interface UserService 
{
	//register
	Response register(Userdto userDto) throws Exception, UnsupportedEncodingException;

	//Login
	ResponseToken login(Logindto loginDto) throws Exception, UnsupportedEncodingException;

	//verification of email
	Response validateEmailId(String token) throws Exception;

	//forgot password?
	Response forgetPassword(Maildto emailDto) throws Exception, UnsupportedEncodingException;

	//Authenticate user
	ResponseToken authentication(Optional<User> user, String password, String email,String token) 
			throws UnsupportedEncodingException, Exception;

	Response setpassword(String emailId, String password);
	
	//Profile Pic
		Response uploadProfilePic(String token, MultipartFile image) throws IOException;

		List<String> showProfile();
}