package com.springBoot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.user.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> 
{
	public Optional<User> findByEmailId(String emailId);
}
