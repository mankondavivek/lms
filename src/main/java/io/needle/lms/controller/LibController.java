package io.needle.lms.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.needle.lms.dto.CustomError;
import io.needle.lms.dto.IssueDetails;
import io.needle.lms.entity.Book;
import io.needle.lms.exception.AvailabilityException;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.service.BookService;
import io.needle.lms.service.LibService;

@RestController
@RequestMapping(path = "lms")
public class LibController {

	public static final Logger logger = LoggerFactory.getLogger(LibController.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private LibService libService;

	@PostMapping("/list/")
	public ResponseEntity<?> listAllBook() {

		List<Book> books = bookService.getAllBooks();
		if (books.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@PostMapping("/issue/new/")
	public ResponseEntity<?> issueBook(@RequestBody IssueDetails issueDetails) {

		logger.info("Issuing Book : {}", issueDetails.getBookId());

		Book book;
		
		try {
			book = libService.issueBook(issueDetails);
		} catch (NotFoundException e) {
			
			logger.error("Unable to issue. Book with id {} not found.", issueDetails.getBookId());

			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
			
		} catch (AvailabilityException e) {
			
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@PostMapping("/return/")
	public ResponseEntity<?> returnBook(@RequestBody IssueDetails returnDetails) {

		logger.info("Returning Book : {}", returnDetails.getBookId());

		Book book;
		try {
			book = bookService.getById( returnDetails.getBookId());
		} catch (NotFoundException e) {
			
			logger.error("Unable to return. Book with id {} not found.",  returnDetails.getBookId());

			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
		}

		try {
			libService.returnBook( returnDetails);
		} catch (NotFoundException e) {
			
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (InvalidDataException e) {
			
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		if (book.getNextAvailableSlot().compareTo(Calendar.getInstance().getTime()) > 0) {
			return new ResponseEntity<>(new CustomError("You are late.However, return is successful."), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/check/")
	public ResponseEntity<?> requestBookAvailability(@RequestBody Long bookId) {

		logger.info(" Book : {}", bookId);

		Book book;
		try {
			book = bookService.getById(bookId);
		} catch (NotFoundException e) {
			logger.error("Book with id {} not found.", bookId);

			return new ResponseEntity<>(new CustomError("Book with id " + bookId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		
		if (book.getNextAvailableSlot().compareTo(Calendar.getInstance().getTime()) > 0) {
			//String message = book.getNextAvailableSlot().toString() ;
			return new ResponseEntity<Date>(book.getNextAvailableSlot(), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(new CustomError("Book with id " + bookId + " not available."),
					HttpStatus.NOT_FOUND);
		}
	}
}
