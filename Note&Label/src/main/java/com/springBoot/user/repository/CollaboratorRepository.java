package com.springBoot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> 
{
	Optional<Collaborator> findByEmailAndNoteEntityId(String email,Long id);
}
