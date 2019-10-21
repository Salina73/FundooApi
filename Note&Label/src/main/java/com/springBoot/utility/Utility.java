package com.springBoot.utility;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.springBoot.user.model.Note;

@Component
public class Utility
{
    private static JavaMailSender javaMailSender;
	
	private static String token;
	
	public Utility(JavaMailSender javaMailSender) 
	{
        Utility.javaMailSender = javaMailSender;
    }
	
	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Utility.token = token;
	}
	
	public static  String send(String toEmail, String subject, String body) 
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try 
		{
			helper.setFrom("salinabodale73@gmail.com");
		    helper.setTo(toEmail);
		    helper.setText(body);
		    helper.setSubject(subject);
		} 
		catch (MessagingException e) 
		{
		    e.printStackTrace();
		    return "Error occured ..";
		}
		javaMailSender.send(message);
		return "Mail Sent Success!";
		  
	}

	public static String sendToken(String emailid, String subject, String link)
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try 
		{
			helper.setFrom("salinabodale73@gmail.com");
		    helper.setTo(emailid);
		    helper.setText(link);
		    helper.setSubject(subject);
		} 
		catch (MessagingException e) 
		{
		    e.printStackTrace();
		    return "Error occured ..";
		}
		javaMailSender.send(message);
		return "Mail Sent Successfully!";
		  
	}
	public static String sendMailForCollaboration(String emailid, String subject, String link)
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try 
		{
			helper.setFrom("salinabodale73@gmail.com");
		    helper.setTo(emailid);
		    helper.setText(link);
		    helper.setSubject(subject);
		} 
		catch (MessagingException e) 
		{
		    e.printStackTrace();
		    return "Error occured ..";
		}
		javaMailSender.send(message);
		return "Mail Sent Successfully!";
		  
	}
	public static String getUrl(Long id) 
	{
		TokenGeneration tokenUtil = new TokenGeneration();
		return "http://localhost:8080/user/" + tokenUtil.createToken(id);
	}

}