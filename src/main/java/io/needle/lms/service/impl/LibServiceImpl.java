package io.needle.lms.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.needle.lms.dto.IssueDetails;
import io.needle.lms.entity.Book;
import io.needle.lms.entity.Student;
import io.needle.lms.exception.AvailabilityException;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.repository.BookRepository;
import io.needle.lms.repository.StudentRepository;
import io.needle.lms.service.LibService;

@Service
public class LibServiceImpl implements LibService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private StudentRepository studentRepository;

	private int noOfDaysAllowed = 14;

	@Override
	public Book issueBook(IssueDetails issueDetails) throws NotFoundException, AvailabilityException {

		Book book = bookRepository.getOne(issueDetails.getBookId());

		if (book == null) {
			throw new NotFoundException("Book not found");
		}

		if (book.getNextAvailableSlot().compareTo(Calendar.getInstance().getTime()) > 0) {
			throw new AvailabilityException(
					"Sorry. The book you requested is available only after " + book.getNextAvailableSlot().toString());
		} else {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, noOfDaysAllowed);

			book.setNextAvailableSlot(cal.getTime());

			Student student = studentRepository.getOne(issueDetails.getStudentId());

			if (student == null) {
				throw new NotFoundException("Student not found");
			}
			book.setIssuedTo(student);

			bookRepository.save(book);

			return book;
		}
	}

	@Override
	public void returnBook(IssueDetails returnDetails) throws NotFoundException, InvalidDataException {
		
		Book book = bookRepository.getOne(returnDetails.getBookId());

		if (book == null) {
			throw new NotFoundException("Book not found");
		}
		
		if(book.getIssuedTo().getId() != returnDetails.getStudentId()) {
			throw new InvalidDataException("Student who issued the book should return it.");
		}
		
		Student student = studentRepository.getOne(returnDetails.getStudentId());

		if (student == null) {
			throw new NotFoundException("Student not found");
		}
		
		book.setIssuedTo(null);
		
		book.setNextAvailableSlot(Calendar.getInstance().getTime());
		
		bookRepository.save(book);
	}

}
