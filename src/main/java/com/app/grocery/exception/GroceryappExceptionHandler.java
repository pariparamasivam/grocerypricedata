package com.app.grocery.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.grocery.model.GroceryPriceDataResponse;

/**
 * 
 * Handles the exception for the application.
 *
 */
@ControllerAdvice
@ResponseBody
public class GroceryappExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles Item not found exception.
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ItemNameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseEntity<List<GroceryPriceDataResponse>> handleItemNameNotFound(
			final Exception exception, final HttpServletRequest request) {
		// Set error details to send in response.
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND.toString());
		// Set Error details in response object.
		GroceryPriceDataResponse groceryPriceDataResponse = new GroceryPriceDataResponse();
		groceryPriceDataResponse.setExceptionResponse(exceptionResponse);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = new ArrayList<>();
		groceryPriceDataResponseList.add(groceryPriceDataResponse);
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles Internal server error.
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ResponseEntity<List<GroceryPriceDataResponse>> handleInternalServer(final Exception exception,
			final HttpServletRequest request) {
		// Set error details to send in response.
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
		exceptionResponse.setErrorMessage(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		// Set Error details in response object.
		GroceryPriceDataResponse groceryPriceDataResponse = new GroceryPriceDataResponse();
		groceryPriceDataResponse.setExceptionResponse(exceptionResponse);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = new ArrayList<>();
		groceryPriceDataResponseList.add(groceryPriceDataResponse);
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}