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
import io.needle.lms.entity.Student;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.InvalidUserNamePasswordException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.service.LoginService;
import io.needle.lms.service.StudentService;

@RestController
public class WelcomeController {
	
	public static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private StudentService studentService;
	
	/**
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/")
	public void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("login.html");
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> loginUser(@RequestBody UserData userData) throws IOException {
		
		if(userData == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> RegisterUser(@RequestBody Student student) throws IOException {
		
		if(student == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Student response;
		try {
			response = studentService.addStudent(student);
		} catch (InvalidDataException e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomError(e.getMessage()));
		}
		
		return new ResponseEntity<Student>(response, HttpStatus.OK);
	}
}