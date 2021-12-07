package com.app.grocery.exception;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.grocery.model.GroceryPriceDataResponse;

@ControllerAdvice
@ResponseBody
public class GroceryappExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ItemNameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseEntity<List<GroceryPriceDataResponse>> handleItemNameNotFound(final Exception exception,
			final HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND.toString());
		GroceryPriceDataResponse groceryPriceDataResponse = new GroceryPriceDataResponse();
		groceryPriceDataResponse.setExceptionResponse(exceptionResponse);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = new ArrayList<>();
		groceryPriceDataResponseList.add(groceryPriceDataResponse);
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ResponseEntity<List<GroceryPriceDataResponse>> handleInternalServer(final Exception exception,
			final HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		GroceryPriceDataResponse groceryPriceDataResponse = new GroceryPriceDataResponse();
		groceryPriceDataResponse.setExceptionResponse(exceptionResponse);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = new ArrayList<>();
		groceryPriceDataResponseList.add(groceryPriceDataResponse);
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		   Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDate.now());
	        body.put("status", status.value());

	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}
	
}