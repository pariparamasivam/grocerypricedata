package com.app.grocery.model;

import java.util.Date;

import com.app.grocery.exception.ExceptionResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryPriceDataResponse {

	private Long id;
	private String itemName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateadded;
	private Double price;
	private ExceptionResponse exceptionResponse; 

}
