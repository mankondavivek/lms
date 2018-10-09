package io.needle.lms.dto;

import lombok.Data;

@Data
public class IssueDetails {
	
	private Long bookId;
	
	private Long studentId;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	
}
