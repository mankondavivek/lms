package io.needle.lms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private boolean isAvailable;

	private Date nextAvailableSlot;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student issuedTo;

	@ManyToMany(mappedBy = "books")
	@JsonIgnoreProperties("books")
	private List<Publication> publications;

	@ManyToOne
	@JoinColumn(name = "library_id")
	private Library library;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getNextAvailableSlot() {
		return nextAvailableSlot;
	}

	public void setNextAvailableSlot(Date nextAvailableSlot) {
		this.nextAvailableSlot = nextAvailableSlot;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Student getIssuedTo() {
		return issuedTo;
	}

	public void setIssuedTo(Student issuedTo) {
		this.issuedTo = issuedTo;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

}
