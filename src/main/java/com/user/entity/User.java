package com.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="user_table")
public class User
{
	 
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long userId;
	
	@NotEmpty(message = "First Name should not be empty ")
	private String firstName;
	private String lastName;
	
	@Size(min = 2, max = 20, message = "Please Enter Valid Address")
	@NotEmpty
	private String address;
	
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Mobile Number")
	private String mobile;
	
	
	private String Password;

	// transient ka use mySql me data nahi pahuchega
	@Transient
	private String repeatPassword;
	
	@Email(message = "Please enter valid email")
	private String email;


}
