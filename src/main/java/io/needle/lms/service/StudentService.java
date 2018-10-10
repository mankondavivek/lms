package io.needle.lms.service;

import io.needle.lms.entity.Student;
import io.needle.lms.exception.InvalidDataException;

public interface StudentService {

	Student addStudent(Student student) throws InvalidDataException;

}
