package io.needle.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.needle.lms.entity.Author;
import io.needle.lms.entity.Book;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
