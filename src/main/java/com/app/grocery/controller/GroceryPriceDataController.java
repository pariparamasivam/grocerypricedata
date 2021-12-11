package com.app.grocery.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.grocery.exception.ItemNameNotFoundException;
import com.app.grocery.exception.constants.CommonConstants;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.service.IGroceryPriceDataService;
import com.app.grocery.service.exception.GroceryPriceDataBusinessException;

import lombok.RequiredArgsConstructor;

/**
 * Get Grocery Price Data information.
 *
 *
 */
@RestController
@RequestMapping("/v1/api/")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class GroceryPriceDataController {

	Logger logger = LoggerFactory.getLogger(GroceryPriceDataController.class);

	@Autowired
	private IGroceryPriceDataService groceryPriceDataService;

	/**
	 * Get a list of vegetable/fruit sorted by name, their maximum price ,date on
	 * which price was max.
	 * 
	 * @return
	 * @throws ItemNameNotFoundException
	 * @throws GroceryPriceDataBusinessException
	 */
	@GetMapping("/getGroceryItemByMaxPrice")
	public ResponseEntity<List<GroceryPriceDataResponse>> getGroceryItemByMaxPriceAndSorted()
			throws GroceryPriceDataBusinessException {
		List<GroceryPriceDataResponse> groceryPriceDataResponseList;
		try {
			logger.info("Inside getGroceryItemByMaxPriceAndSorted {}", this.getClass());
			groceryPriceDataResponseList = groceryPriceDataService.findGroceryByMaxPriceAndSorted();
			if (groceryPriceDataResponseList.isEmpty()) {
				logger.error("Item Not Found for requested URL /getGroceryItemByMaxPrice");
				throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
			}
		} catch (ItemNameNotFoundException e) {
			logger.error(e.getMessage() + " error occured in getGroceryItemByMaxPriceAndSorted {}", this.getClass());
			throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
		} catch (GroceryPriceDataBusinessException e) {
			logger.error("GroceryPriceDataBusinessException :" + e.getMessage()
					+ " occured in getGroceryItemByMaxPriceAndSorted", this.getClass());
			throw new GroceryPriceDataBusinessException(CommonConstants.SERVICE_EXCEPTION_MSG);
		}
		logger.info("Inside getGroceryItemByMaxPriceAndSorted", this.getClass());
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.OK);
	}

	/**
	 * Get Grocery Item details sorted by name.
	 * 
	 * @param itemName
	 * @return
	 * @throws ItemNameNotFoundException
	 * @throws GroceryPriceDataBusinessException
	 */
	@GetMapping("/getAllGroceryItemsSortByName")
	public ResponseEntity<List<GroceryPriceDataResponse>> getAllGroceryItemsSortByName(
			@NotBlank @RequestParam(name = "itemName", required = true) String itemName)
			throws GroceryPriceDataBusinessException {
		List<GroceryPriceDataResponse> groceryPriceDataResponseList;
		try {
			logger.info("Inside getAllGroceryItemsSortByName {}", this.getClass());
			groceryPriceDataResponseList = groceryPriceDataService.findByItemName(itemName);
			if (groceryPriceDataResponseList.isEmpty()) {
				logger.error("Item Not Found for requested URL /getAllGroceryItemsSortByName");
				throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
			}
		} catch (ItemNameNotFoundException e) {
			logger.error(e.getMessage() + " error occured in getGroceryItemByMaxPriceAndSorted", this.getClass());
			throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
		} catch (GroceryPriceDataBusinessException e) {
			logger.error("GroceryPriceDataBusinessException :" + e.getMessage()
					+ " occured in getGroceryItemByMaxPriceAndSorted", this.getClass());
			throw new GroceryPriceDataBusinessException(CommonConstants.SERVICE_EXCEPTION_MSG);
		}
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.OK);
	}

	/**
	 * Get Grocery Item Name which has max price by search text.
	 * 
	 * @param itemName
	 * @return
	 * @throws ItemNameNotFoundException
	 * @throws GroceryPriceDataBusinessException
	 */
	@GetMapping("/getAllGroceryItemsByNameContains")
	public ResponseEntity<List<GroceryPriceDataResponse>> getAllGroceryItemsByNameContains(
			@NotBlank @RequestParam(name = "itemName", required = true) @Valid String itemName)
			throws GroceryPriceDataBusinessException {
		List<GroceryPriceDataResponse> groceryPriceDataResponseList;
		try {
			logger.info("Inside getAllGroceryItemsByNameContains {}", this.getClass());
			groceryPriceDataResponseList = groceryPriceDataService.findByItemNameContaining(itemName);
			if (groceryPriceDataResponseList.isEmpty()) {
				logger.error("Item Not Found for requested URL /getAllGroceryItemsByNameContains");
				throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
			}
		} catch (ItemNameNotFoundException e) {
			logger.error(e.getMessage() + " error occured in getAllGroceryItemsByNameContains", this.getClass());
			throw new ItemNameNotFoundException(CommonConstants.ITEM_NOT_FOUND_MSG);
		} catch (GroceryPriceDataBusinessException e) {
			logger.error("GroceryPriceDataBusinessException :" + e.getMessage()
					+ " occured in getAllGroceryItemsByNameContains", this.getClass());
			throw new GroceryPriceDataBusinessException(CommonConstants.SERVICE_EXCEPTION_MSG);
		}
		return new ResponseEntity<List<GroceryPriceDataResponse>>(groceryPriceDataResponseList, HttpStatus.OK);
	}
}
