package io.needle.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.needle.lms.dto.CustomError;
import io.needle.lms.entity.Book;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.service.BookService;

@RestController
@RequestMapping(path = "lms/book")
public class BookController {

	public static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@GetMapping("/all/")
	public ResponseEntity<?> getAllBooks() {

		List<Book> books = bookService.getAllBooks();
		if (books.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@GetMapping(value = "/book/{id}")
	public ResponseEntity<?> getBook(@PathVariable("id") long id) {

		logger.info("Fetching Book with id {}", id);
		Book book;
		try {
			book = bookService.getById(id);
		} catch (NotFoundException e) {

			logger.error("Book with id {} not found.", id);
			return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@PostMapping(value = "/add/")
	public ResponseEntity<?> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {

		logger.info("Creating Book : {}", book);

		if (bookService.isBookExist(book.getId())) {
			logger.error("Unable to create. A Book with name {} already exist", book.getName());
			return new ResponseEntity<>(
					new CustomError("Unable to create. A Book with name " + book.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		try {
			bookService.addBook(book);
		} catch (InvalidDataException e) {
			return new ResponseEntity<>(
					new CustomError(e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/lms/book/{id}").buildAndExpand(book.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@PutMapping(value = "/book/{id}")
	public ResponseEntity<?> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
		logger.info("Updating Book with id {}", id);

		Book currentBook;
		try {
			currentBook = bookService.getById(id);
		} catch (NotFoundException e) {

			logger.error("Unable to update. Book with id {} not found.", id);
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
		}

		currentBook.setName(book.getName());
		currentBook.setAuthor(book.getAuthor());
		currentBook.setAvailable(book.isAvailable());
		currentBook.setIssuedTo(book.getIssuedTo());
		currentBook.setLibrary(book.getLibrary());
		currentBook.setNextAvailableSlot(book.getNextAvailableSlot());
		currentBook.setPublications(book.getPublications());

		Book book1;
		try {
			book1 = bookService.updateBook(currentBook);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Book>(book1, HttpStatus.OK);
	}

	@DeleteMapping(value = "/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {

		logger.info("Fetching & Deleting Book with id {}", id);


		try {
			bookService.deleteBookById(id);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/book/all")
	public ResponseEntity<?> deleteAllBooks() {

		logger.info("Deleting All Books");

		try {
			bookService.deleteAllBooks();
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
