package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	
	//name se data deke
		public User findByFirstName(String fname);
		
		
		
		//ek name ka sara data dekhe
		//public List<User> findByFirstName(String fName);
		
		
		//eddress se data search kare
		//public User findByAddress(String address);
		
		
		
		
		public List<User> findByAddress(String address);
		
		
		//first name last name se data search kare
		public User findByFirstNameAndLastName(String fName, String lName);
		
		
		public Optional<User> findByEmail(String email);
		
		
	}
