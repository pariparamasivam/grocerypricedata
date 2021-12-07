package com.app.grocery.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroceryPriceDataBusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroceryPriceDataBusinessException(final String message) {
		super(message);
	}
}
