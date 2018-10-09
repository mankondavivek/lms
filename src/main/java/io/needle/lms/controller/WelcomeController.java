package io.needle.lms.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.needle.lms.dto.CustomError;
import io.needle.lms.dto.UserData;
import io.needle.lms.exception.InvalidUserNamePasswordException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.service.LoginService;

@RestController
public class WelcomeController {
	
	public static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/")
	public void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("login.html");
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> validateUser(@RequestBody UserData userData) throws IOException {
		
		if(userData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		boolean isValid = false;
		try {
			isValid = loginService.validateUser(userData.getUsername(), userData.getPassword());
		} 
		catch (NotFoundException e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomError(e.getMessage()));
		} 
		catch (InvalidUserNamePasswordException e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomError(e.getMessage()));
		}
		
		if(isValid) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}