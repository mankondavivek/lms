package io.needle.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.needle.lms.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long>{

}
