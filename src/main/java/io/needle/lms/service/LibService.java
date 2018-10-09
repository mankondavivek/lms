package io.needle.lms.service;

import io.needle.lms.dto.IssueDetails;
import io.needle.lms.entity.Book;
import io.needle.lms.exception.AvailabilityException;
import io.needle.lms.exception.InvalidDataException;
import io.needle.lms.exception.NotFoundException;

public interface LibService {

	Book issueBook(IssueDetails issueDetails) throws NotFoundException, AvailabilityException;

	void returnBook(IssueDetails returnDetails) throws NotFoundException, InvalidDataException;

}
