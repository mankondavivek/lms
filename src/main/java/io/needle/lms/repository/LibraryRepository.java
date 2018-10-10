package io.needle.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.needle.lms.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>{

}
