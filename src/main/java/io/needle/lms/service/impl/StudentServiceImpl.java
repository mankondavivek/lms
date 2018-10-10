package io.needle.lms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.needle.lms.entity.Student;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.repository.StudentRepository;
import io.needle.lms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Student addStudent(Student student) throws InvalidDataException {

		if (student.getUsername() == null || student.getPassword() == null || student.getUsername() == ""
				|| student.getPassword() == "") {

			throw new InvalidDataException("Invalid username or password");

		}
		
		Student response = studentRepository.save(student);
		
		return response;
	}

}
