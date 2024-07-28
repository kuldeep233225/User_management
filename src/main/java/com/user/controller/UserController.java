package com.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.user.entity.User;
import com.user.service.EmailService;
import com.user.service.FileService;
import com.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")

public class UserController 
{
	@Autowired
	private UserService userService;
	//uplod image
	@Autowired
	private FileService fileService;

	
	// fetch path from application.properties
	@Value("${project.image}")
	String path;

	@Autowired
	private EmailService emailService;
	
	
	//Mysql me data pahuchane ke liye
	@PostMapping("/postdata")
	public ResponseEntity<?> saveData(@Valid @RequestBody User user) {

		// this is send notification 
		boolean sendEmailNotification = this.emailService.sendEmailNotification(user.getEmail());
		
		if (sendEmailNotification) {
 
			// here data stored in db.
			User saveDataService = userService.saveDataService(user);
			return new ResponseEntity<>(saveDataService, HttpStatus.CREATED);

		}

		return new ResponseEntity<>("Sorry Email Not send ", HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	
	
	//Mysql ka koyi ek data show jo id number typ hoga
	@GetMapping("/get/{userId}")

	public User getData(@PathVariable Long userId) {
		return userService.getData(userId);
	}
	
	
	
	
	
	// Mysql ka all data show
	@GetMapping("/getall")
	public List<User> getAllData() {
		return userService.getAllData();
	}

	
	//localhost:9090/user/update/Id Type karo then update
	@PutMapping("/update/{id}")
	public User updateData(@Valid @RequestBody User user,@PathVariable("id") Long userId) {
		return userService.updateData(user, userId);
	}
	
	
	
	// delete Data

		@DeleteMapping("delete/{userId}")
		public String deleteData(@PathVariable Long userId) {
			return userService.deleteData(userId);
		}
	
		
		// Fetch Data By Name
		//name se data ko search kare

		
		  @GetMapping("/name") public User findByName(@RequestParam String name) {
		 return userService.fetchData(name); }
		 
		
		//ek name ka sara data dekhe
		//@GetMapping("/allname")
		//public List<User> findAllByName(@RequestParam String name)
		//{
		//	return userService.feachAllByName(name);
		//}
		  
		  
		//eddress se data search kare
		//@GetMapping("/address")
		//public User findByAddress(@RequestParam String address) {
		//return userService.fetchbyAddress(address);
		//}
		
		
		
		//sem adress ka all data show hoga
		@GetMapping("/byaddress")
		public List<User> findAllByAddress(@RequestParam String address) {

			return userService.fetchAllByAddress(address);
		}
		
		//First name and Last name se data search kare
		@GetMapping("/fnamelname")
		public User findByfNamelName(@RequestParam String fName,@RequestParam String lName) {
			return userService.fetchByFNameAndLname(fName, lName);
		}
		
		
		
		
		// pagination
		
		@GetMapping("/getAllData")

		public ResponseEntity<Page<User>> fetchAllData(@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "2") int size) {
			PageRequest pages = PageRequest.of(page, size);

			Page<User> allData = userService.fechDataWithPagination(pages);
			return new ResponseEntity<>(allData, HttpStatus.OK);
		}
		
		@GetMapping("/getAll/{page}/{size}")
		public ResponseEntity<?> getAllDataWithPagination(@PathVariable int page, @PathVariable int size) {
			PageRequest p = PageRequest.of(page, size);

			Page<User> allData = userService.fechDataWithPagination(p);

			return new ResponseEntity<>(allData, HttpStatus.OK);
		}
		
		
		
		//Uplod image
		@PostMapping("/postimage")
		public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file) {

			try {
				this.fileService.saveImage(path, file);
			} catch (IOException e) {

				e.printStackTrace();

				return new ResponseEntity<>("Image Not uploaded .....", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>("Image Uploaded SuccessFully ...", HttpStatus.OK);

		}

}
