package com.userTest;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;

import com.springBoot.response.Response;
import com.springBoot.user.dto.Userdto;
import com.springBoot.user.model.User;
import com.springBoot.user.repository.UserRepo;
import com.springBoot.user.service.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserTest {

	@Mock
	UserRepo userRepo;
	
	@Mock
	ModelMapper modelMapper;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	 Response response;
	
	@Mock
	UserServiceImpl userServiceImpl;
	
	@Test
	public void testUserRegistration() {
		User user = new User();
		Userdto userDto=new Userdto();
		user.setFirstName("Mugdha");
		user.setLastName("Nagale");
		user.setMobileNum("9876543215");
		user.setEmailId("mugdhanagale07@gmail.com");
		user.setPassword("Salina@1234");
		userRepo.save(user);
//		when(modelMapper.map(userDto, User.class)).thenReturn(user);
//		when(userRepo.findByEmailId(user.getEmailId())).thenReturn(Optional.ofNullable(user));
//		when(passwordEncoder.encode(userDto.getPassword())).thenReturn(user.getPassword());
//		when(userRepo.save(user)).thenReturn(user);
//		Response response = userServiceImpl.register(userDto);
		assertEquals(200, response.getStatusCode());
	}
	
}
