package com.user.exception;



import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {

		// invalid data enter karne par message show hoga
	  @ExceptionHandler(MethodArgumentNotValidException.class) public Map<String,
	  String> hanlderInvalidException(MethodArgumentNotValidException ex) {
	  Map<String, String> errorMap = new HashMap<>();
	  
	  ex.getBindingResult().getFieldErrors().forEach(error ->
	  
	  { errorMap.put(error.getField(), error.getDefaultMessage()); });
	  
	  return errorMap;
	  
	  }
	// id wrong enter karne par jo exception aaye usko haindale hota hai
		@ExceptionHandler(UserNotFoundException.class)
		public ResponseEntity<?> cutstomExceptionHanler(UserNotFoundException ex, WebRequest request) {

			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

		}
		
		
		
		@ExceptionHandler(UserAlreadyExist.class)
		public ResponseEntity<?> cutstomExceptionHanler(UserAlreadyExist ex, WebRequest request) {

			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);

		}

		@ExceptionHandler(MisMatchPassword.class)
		public ResponseEntity<?> cutstomExceptionHanler(MisMatchPassword ex, WebRequest request) {

			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);

		}
	
}
