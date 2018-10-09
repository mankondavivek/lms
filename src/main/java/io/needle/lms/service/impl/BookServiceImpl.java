package io.needle.lms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.needle.lms.entity.Book;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;
import io.needle.lms.repository.BookRepository;
import io.needle.lms.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> getAllBooks() {

		List<Book> books = bookRepository.findAll();

		return books;
	}

	@Override
	public Book getById(Long bookId) throws NotFoundException {

		Book book = bookRepository.getOne(bookId);
		if (book == null) {
			throw new NotFoundException("Book not found");
		}
		return book;
	}

	@Override
	public void addBook(Book book) throws InvalidDataException {

		if (book.getName() == null || book.getName() == "") {
			throw new InvalidDataException("Data invalid");
		}

		bookRepository.save(book);
	}

	@Override
	public Book updateBook(Book currentBook) throws NotFoundException {

		Book book = bookRepository.getOne(currentBook.getId());

		if (book == null) {
			throw new NotFoundException("Book not found");
		}

		book.setAuthor(currentBook.getAuthor());
		book.setAvailable(currentBook.isAvailable());
		book.setIssuedTo(currentBook.getIssuedTo());
		book.setLibrary(currentBook.getLibrary());
		book.setName(currentBook.getName());
		book.setNextAvailableSlot(currentBook.getNextAvailableSlot());
		book.setPublications(currentBook.getPublications());

		bookRepository.save(book);

		return book;
	}

	@Override
	public void deleteBookById(Long id) throws NotFoundException {
		Book book = bookRepository.getOne(id);

		if (book == null) {
			throw new NotFoundException("Book not found");
		}

	}

	@Override
	public void deleteAllBooks() throws NotFoundException {
		if(bookRepository.findAll().isEmpty()) {
			throw new NotFoundException("No books not found");
		}
		bookRepository.deleteAll();
	}

	@Override
	public boolean isBookExist(Long id) {

		Book book = bookRepository.getOne(id);
		if (book == null) {
			return false;
		}
		return true;
	}

}
