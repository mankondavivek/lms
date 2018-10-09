package io.needle.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.needle.lms.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	Student findByUsername(String username);

}
