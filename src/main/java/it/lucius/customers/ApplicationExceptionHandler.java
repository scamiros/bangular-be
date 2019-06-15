package it.lucius.customers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler({ApplicationException.class})
	public @ResponseBody ResponseEntity<Object> handleEx(ApplicationException ex) {
		
		ResponseEntity<Object> res = new ResponseEntity<Object>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
		return res;
	}
}
