package io.needle.lms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Library{
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/*@OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
	private List<Book> books;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
