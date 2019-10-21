package com.springBoot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> 
{
	public Label findByLabelidAndUserId(Long labelid,Long userid);
	
	public Optional<Label> findByLabelName(String labelName);
}