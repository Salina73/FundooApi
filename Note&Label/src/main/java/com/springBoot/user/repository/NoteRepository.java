package com.springBoot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.Note;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> 
{
	public Note findByUseridAndNoteid(Long userid,Long noteid);

	public Note findByUserid(Long id);
	
	public Note findByUseridAndTitle(Long userid,String title);
}