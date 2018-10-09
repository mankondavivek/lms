package io.needle.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.needle.lms.entity.Student;
import io.needle.lms.exception.InvalidUserNamePasswordException;
import io.needle.lms.repository.StudentRepository;
import io.needle.lms.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public boolean validateUser(String username, String password) throws InvalidUserNamePasswordException {
		
		Student student = studentRepository.findByUsername(username);
		
		if(student == null) {
			throw new InvalidUserNamePasswordException("Either Username or Password is incorrect.");
		}
		
		if(student.getPassword().equals(password)) {
			return true;
		}
		else {
			throw new InvalidUserNamePasswordException("Either Username or Password is incorrect.");
		}
	}

}
