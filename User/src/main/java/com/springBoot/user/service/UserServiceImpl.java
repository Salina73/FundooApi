package com.springBoot.user.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaFileManager.Location;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.springBoot.exception.Exception;
import com.springBoot.rabbit.MessageListener;
import com.springBoot.rabbit.RabbitMqProducer;
import com.springBoot.response.Response;
import com.springBoot.response.ResponseToken;

import com.springBoot.user.dto.Logindto;
import com.springBoot.user.dto.Maildto;

import com.springBoot.user.dto.Userdto;

import com.springBoot.user.model.User;

import com.springBoot.user.repository.UserRepo;

import com.springBoot.utility.ResponseHelper;
import com.springBoot.utility.TokenGeneration;
import com.springBoot.utility.Utility;
import org.springframework.util.StringUtils;

@Component
@SuppressWarnings("unused")
@PropertySource("classpath:message.properties")
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenGeneration tokenUtil;

	@Autowired
	TokenGeneration token1;

	@Autowired
	private Response statusResponse;
	@Autowired
	private Utility utility;

	@Autowired
	private Environment environment;

	
	@Autowired
	private RedisTemplate<String,Object> redisTemp;

	@Value("${key}")
	private String key;
	
	@Value("${profilePath}")
	private final Path filePath=null;

	private final Path noteImagePath=null;
	
	@Autowired 
	private RabbitMqProducer rabbitMqProducer;
	
	public Response register(Userdto userDto) {
		User user = modelMapper.map(userDto, User.class);
		Optional<User> alreadyPresent = userRepo.findByEmailId(user.getEmailId());
		if (alreadyPresent.isPresent()) {
			throw new Exception(environment.getProperty("status.register.emailExistError"));
		}

		String password = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(password);
		user.setProfilePic((filePath.resolve("img_avatar3.png ")).toString());
		user = userRepo.save(user);
		String token1 = tokenUtil.createToken(user.getUserId());
				
		rabbitMqProducer.sendSimpleMessage("Your mail is sent!!!",user.getEmailId(), "Verification is required for login.Please click below link.\n",
				"http://localhost:9092/user/" + token1);
		
		//Utility.sendToken(user.getEmailId(), "Verification is required for login.Please click below link.\n",
			//	"http://localhost:8080/user/" + token1);
		
		statusResponse = ResponseHelper.statusResponse(200, "register successfully");
		return statusResponse;
	}

	public ResponseToken login(Logindto loginDto) {

		Optional<User> user = userRepo.findByEmailId(loginDto.getEmailId());

		ResponseToken response = new ResponseToken();
		if (user.isPresent()) {
			String token = tokenUtil.createToken(user.get().getUserId());
			System.out.println("password..." + (loginDto.getPassword()));
			
			List<String> n=new ArrayList<String>();
			n.add(user.get().getEmailId());
			n.add(user.get().getFirstName());
			n.add(user.get().getLastName());
			if(user.get().getProfilePic()==null) 
			{
				user.get().setProfilePic("https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png");
				userRepo.save(user.get());
			}
			n.add(user.get().getProfilePic());
			redisTemp.opsForValue().set(key, n);
			
			return authentication(user, loginDto.getPassword(), loginDto.getEmailId(), token);
		} else {
			response.setStatusCode(404);
			response.setStatusMessage("User not found");
			return response;
		}

	}

	public ResponseToken authentication(Optional<User> user, String password, String email, String token) {

		ResponseToken response = new ResponseToken();
		if (!user.get().isVerify()) {
			response.setStatusCode(401);
			response.setStatusMessage(environment.getProperty("user.login.verification"));
			return response;
		}

		else if (user.get().isVerify()) {
			boolean status = passwordEncoder.matches(password, user.get().getPassword());
			System.out.println("status" + status);
			if (status == true) {
				System.out.println("logged in");
				response.setStatusCode(200);
				response.setToken(token);
				response.setStatusMessage(environment.getProperty("user.login"));
				return response;
			} else if (status == false) {
				response.setStatusCode(201);
				response.setStatusMessage("Incorrect password");
				return response;
			}
		}
		throw new Exception(401, environment.getProperty("user.login.verification"));
	}

	public Response validateEmailId(String token) {
		System.out.println("Hi");
		Long id = tokenUtil.decodeToken(token);
		User user = userRepo.findById(id)
				.orElseThrow(() -> new Exception(404, environment.getProperty("user.validation.email")));
		user.setVerify(true);
		userRepo.save(user);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("user.validation"));
		return statusResponse;
	}

	public Response forgetPassword(Maildto emailDto) throws Exception, UnsupportedEncodingException {
		Optional<User> alreadyPresent = userRepo.findByEmailId(emailDto.getEmailId());

		if (!alreadyPresent.isPresent()) {
			throw new Exception(401, environment.getProperty("user.forgetPassword.emailId"));
		} else {
			Utility.send(emailDto.getEmailId(), "Click below link to reset password",
					" http://localhost:4200/Reset-Password");
		}
		return ResponseHelper.statusResponse(200, environment.getProperty("user.forgetpassword.link"));
	}

	@Override
	public Response setpassword(String emailId, String password) {
		Optional<User> alreadyPresent = userRepo.findByEmailId(emailId);

		if (!alreadyPresent.isPresent()) {
			throw new Exception(401, environment.getProperty("user.forgetPassword.emailId"));
		} else {
			Long id = alreadyPresent.get().getUserId();

			User user = userRepo.findById(id).orElseThrow(() -> new Exception("User not found!!"));

			String password1 = passwordEncoder.encode(password);

			user.setPassword(password1);
			userRepo.save(user);
			return ResponseHelper.statusResponse(200, "Password set successfully...");
		}
	}

	@Override
	public Response uploadProfilePic(String token, MultipartFile image) throws IOException {
		Long id = token1.decodeToken(token);
		Optional<User> user = userRepo.findById(id);
		if (!user.isPresent())
			throw new Exception(404, "User not present");
		else {
			String fileName = StringUtils.cleanPath(image.getOriginalFilename());
			
			try {
				Files.copy(image.getInputStream(), filePath.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
				
				Path image1 = this.filePath.resolve(fileName);
				
				Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				   			  "cloud_name", "salina",
				   			  "api_key", "589647733871687",
				   			  "api_secret", "Itn_CatKK68_f2wDMBYUjlDLyOE"));
				   		         
				File toUpload = new File(image1.toString());
				@SuppressWarnings("rawtypes")
				Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
				    
				System.out.println(uploadResult);
				
				user.get().setProfilePic(uploadResult.get("secure_url").toString());
				userRepo.save(user.get()); 
				
				List<String> n=new ArrayList<String>();
				n.add(user.get().getEmailId());
				n.add(user.get().getFirstName());
				n.add(user.get().getLastName());
				n.add(user.get().getProfilePic());
				redisTemp.opsForValue().set(key, n);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseHelper.statusResponse(200, "Profile picture is successfully uploaded");
	}

	@Override
	public List<String> showProfile() {
		return (List<String>) redisTemp.opsForValue().get(key);
	}

}