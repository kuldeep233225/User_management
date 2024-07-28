package com.user.service;



import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.user.exception.UserAlreadyExist;
import com.user.exception.MisMatchPassword;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	//Mysql me data pahuchane ke liye
	private UserRepository userRepository;

	public User saveDataService(User user) {
		
		//Email alrady SQL me ho to nahi jayega
		//Email alrady exist
		Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

		if (byEmail.isPresent()) {

			throw new UserAlreadyExist("User Already Exist!  " + user.getEmail());
		}
	
		
		//Password missmatch
		if (!user.getPassword().equals(user.getRepeatPassword())) {
			throw new MisMatchPassword("Sorry Mismatch Password ");
		}
		
		//password encript karne ke liye
		// use dependancy <spring-security-crypto>
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

		String encode = bcryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encode);

		User save = userRepository.save(user);

		return save;
	}
	
	
	
	//Mysql ka koyi ek data show jo id number typ hoga
	public User getData(Long userId)
	{
		// galat data type karne par exception ko haindale kare
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found with Id :" + userId));
	}
	
	
	
	
	
	
	    // Mysql ka all data show
		public List<User> getAllData() {
			return userRepository.findAll();
		}

		
		
		
		
		
	
		//localhost:9090/user/path/Id Type karo then update
		public User updateData(User user, Long userId) {
			User byId = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("User Not Found with Id :" + userId));

			byId.setFirstName(user.getFirstName());
			byId.setLastName(user.getLastName());
			byId.setAddress(user.getAddress());
			byId.setMobile(user.getMobile());

			return userRepository.save(byId);
		}
		
		
		
		// delete data by Id

		public String deleteData(Long userId) {
			User byId = userRepository.findById(userId).get();
			userRepository.delete(byId);
			return "Data SuccussFully Deleted";
		}

		
		// Fetch Data By Name
		//name se data search kare

		
		public User fetchData(String name) {
			User byFirstName = userRepository.findByFirstName(name);

			if (Objects.isNull(byFirstName)) {
				throw new UserNotFoundException("User Not Found with name :" + name);
			}
			return byFirstName;
		}
		 
		
		
		//ek name ka sara data dekhe
		//public List<User> feachAllByName(String name)
		//{
		//	return userRepository.findByFirstName(name);
		//}
		
		
		//eddress se data search kare
		//public User fetchbyAddress(String address) {
		//return userRepository.findByAddress(address);
		//}
		
		
		
		// Fetch All By Address
		//sem adress ka all data show hoga
		public List<User> fetchAllByAddress(String address) {
			return userRepository.findByAddress(address);
		}
		
		
		
		// fetch By FirstName And Last Name

		public User fetchByFNameAndLname(String fName, String lName) {
			return userRepository.findByFirstNameAndLastName(fName, lName);
		}
		
		
		
		public Page<User> fechDataWithPagination(Pageable pageable) {
			Page<User> pages = userRepository.findAll(pageable);
			return pages;
		}
		
}
