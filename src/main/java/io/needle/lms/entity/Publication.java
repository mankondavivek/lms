package io.needle.lms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import lombok.Data;

@Entity
@Data
public class Publication {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	 @ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable(name = "book_publications", 
	 			joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"), 
	 			inverseJoinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "id"))
	private List<Book> books;

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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
