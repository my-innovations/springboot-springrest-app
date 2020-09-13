package com.springboot.exception.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.error.ErrorDetails;
import com.springboot.exception.UserNotFoundException;


@ControllerAdvice
@RestController
public class AllExceptionsHandler extends ResponseEntityExceptionHandler{
	
	//@ExceptionHandler(value=Exception.class) //OK
	//@ExceptionHandler(value = {Exception.class}) //OK
	@ExceptionHandler(value={NullPointerException.class,Exception.class}) //OK
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<ErrorDetails> handleExceptions(Exception ex, WebRequest req) {
		
		String errMsg = ex.getLocalizedMessage();
		
		if (errMsg == null)
			errMsg = ex.toString();
		
		List<String> errDetails = new ArrayList<String>();
		errDetails.add(errMsg);
		
		ErrorDetails errResp = new ErrorDetails(new Date(),errDetails);
		
		//return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		//OR
		//return new ResponseEntity<Object>(ex.getMessage(),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		//OR
		//return new ResponseEntity<>(errResp, HttpStatus.INTERNAL_SERVER_ERROR); //OK
		//OR
		return new ResponseEntity<ErrorDetails>(errResp,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	//@ExceptionHandler(value= UserNotFoundException.class)//OK
	@ExceptionHandler(value= {UserNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<ErrorDetails> handleExceptions(UserNotFoundException ex, WebRequest req) {
		
		String errMsg = ex.getLocalizedMessage();
		
		if (errMsg == null) {
			errMsg = ex.toString();
		}
		
		List<String> errors = new ArrayList<String>();
		errors.add(errMsg);
		
		ErrorDetails errDetails = new ErrorDetails(new Date(),errors);

		//return new ResponseEntity<ErrorDetails>(errResp, HttpStatus.INTERNAL_SERVER_ERROR); //OK
		//OR
		return new ResponseEntity<ErrorDetails>(errDetails,new HttpHeaders(), HttpStatus.NOT_FOUND);
	
	}
	
	/**
	 * ########################################################################################################################
	 * Handling invalid data validation failed (For @Valid) 
	 * ########################################################################################################################
	 */
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       
		//way-01(using List),  Here we are returning our custom error class obj or map object as response.
		/*List<String> errList = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
        	errList.add(error.getDefaultMessage());
        }
        ErrorDetails errors = new ErrorDetails(new Date(),errList);*/
       
        //OR
		
        //way-02(Using Map)
        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("dateTime", new Date());
        errors.put("status", status.value());
        List<String> errList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        errors.put("errors", errList);
        
        // return new ResponseEntity<Object>(errors,HttpStatus.status); //OK
        //OR        
        return new ResponseEntity<Object>(errors, new HttpHeaders(),HttpStatus.BAD_REQUEST); //OK
    }

}
