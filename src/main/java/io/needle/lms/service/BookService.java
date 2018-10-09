package io.needle.lms.service;

import java.util.List;

import io.needle.lms.entity.Book;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;

public interface BookService {

	public List<Book> getAllBooks();

	public Book getById(Long bookId) throws NotFoundException;

	public boolean isBookExist(Long long1);

	public void addBook(Book book) throws InvalidDataException;

	public Book updateBook(Book currentBook) throws NotFoundException;

	public void deleteBookById(Long id) throws NotFoundException;

	public void deleteAllBooks() throws NotFoundException;

}
