package com.hungerbox.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hungerbox.dto.ErrorResponsedto;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ErrorResponsedto> handleException(UserNotFoundException userException) {
		ErrorResponsedto response = new ErrorResponsedto();
		response.setStatusCode("404 non found");
		response.setStatusMessage(userException.getMessage());
		return new ResponseEntity<ErrorResponsedto>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = FoodItemNotFoundException.class)
	public ResponseEntity<ErrorResponsedto> handleException(FoodItemNotFoundException foodException) {
		ErrorResponsedto response = new ErrorResponsedto();
		response.setStatusCode("404 non found");
		response.setStatusMessage(foodException.getMessage());
		return new ResponseEntity<ErrorResponsedto>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = VendorNotFoundException.class)
	public ResponseEntity<ErrorResponsedto> handleException(VendorNotFoundException vendorException) {
		ErrorResponsedto response = new ErrorResponsedto();
		response.setStatusCode("404 non found");
		response.setStatusMessage(vendorException.getMessage());
		return new ResponseEntity<ErrorResponsedto>(response, HttpStatus.BAD_REQUEST);

	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException argInvalidException,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponsedto response = new ErrorResponsedto();
		response.setStatusCode("505 not a valid Credentials");
		String allFieldErrors = argInvalidException.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
		response.setStatusMessage(allFieldErrors);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
}