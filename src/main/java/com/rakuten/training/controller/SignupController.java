package com.rakuten.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.model.Nuser;
import com.rakuten.training.repository.SignupRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/v2")
public class SignupController {
	
	@Autowired
	private SignupRepo signuprepo;
	
	
	//save the user
	@PostMapping("/signup")
	public ResponseEntity<Nuser> signupUser(@RequestBody Nuser user)
	{
		try {
			String EmailId=user.getEmailid();
			
		     if(EmailId!=null || EmailId!="")
			{
				Nuser userExist=signuprepo.findByemailid(user.getEmailid());
				if(userExist!=null)
				{
					throw new Exception("user with this email");
				}
				
			}

				Nuser localUser=new Nuser();
				localUser.setEmailid(user.getEmailid());
				localUser.setRole(user.getRole());
				localUser.setPassword(user.getPassword());
				localUser.setUsername(user.getUsername());
				signuprepo.save(localUser);
				return new ResponseEntity<>(localUser,HttpStatus.CREATED);
			
		}
		catch(Exception e)
		{
			
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//for log in user
	
	@PostMapping("/login")
	public ResponseEntity<Nuser> loginUser(@RequestBody Nuser user)
	{
		try {
			String EmailId=user.getEmailid();
			String PassWord=user.getPassword();
			String Role=user.getRole();
			
		     if(EmailId!=null && EmailId!="" && PassWord!=null && PassWord!="" && Role!=null && Role!="" )
			{
				Nuser userExist=signuprepo.findByemailidAndPasswordAndRole(EmailId,PassWord,Role);
				if(userExist==null)
				{
					throw new Exception("either the email or password is wrong");
				}
				return new ResponseEntity<>(userExist,HttpStatus.OK);
			}
		     
		     throw new Exception("something is missing");
		     
			
		}
		catch(Exception e)
		{
			
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
