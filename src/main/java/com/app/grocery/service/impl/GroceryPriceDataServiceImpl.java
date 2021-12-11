package com.app.grocery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grocery.entity.GroceryPriceData;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.repository.GroceryPriceDataRepository;
import com.app.grocery.service.IGroceryPriceDataService;
import com.app.grocery.service.exception.GroceryPriceDataBusinessException;

/**
 * 
 * Get Grocery Price Data information and convert into Response object.
 *
 */
@Service
public class GroceryPriceDataServiceImpl implements IGroceryPriceDataService {

	Logger logger = LoggerFactory.getLogger(GroceryPriceDataServiceImpl.class);

	@Autowired
	GroceryPriceDataRepository groceryPriceDataRepository;

	@Autowired
	ModelMapper modelMapper;

	/**
	 * Get max price of items with date, sorted by name, max price and date Converts
	 * entity object into response object.
	 */
	@Override
	public List<GroceryPriceDataResponse> findGroceryByMaxPriceAndSorted() throws GroceryPriceDataBusinessException {
		logger.info("Inside findGroceryByMaxPriceAndSorted {}", this.getClass());
		List<GroceryPriceData> groceryPriceDataList = groceryPriceDataRepository.findGroceryByMaxPriceAndSorted();
		logger.info("Fetch data in findGroceryByMaxPriceAndSorted method", this.getClass());
		return convertToResponseModel(groceryPriceDataList);
	}

	/**
	 * Get max price of items with date, sorted by name, max price and date and by
	 * item name search text. Converts entity object into response object.
	 */
	@Override
	public List<GroceryPriceDataResponse> findByItemName(String itemName) throws GroceryPriceDataBusinessException {
		logger.info("Inside findGroceryByMaxPriceAndSorted {}", this.getClass());
		List<GroceryPriceData> groceryPriceDataList = groceryPriceDataRepository.findByItemName(itemName);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = convertToResponseModel(groceryPriceDataList);
		logger.info("Converted entity object to response object in findByItemName.", this.getClass());
		return groceryPriceDataResponseList;
	}

	/**
	 * Get item details by item name and converts entity object into response
	 * object.
	 */
	@Override
	public List<GroceryPriceDataResponse> findByItemNameContaining(String itemName)
			throws GroceryPriceDataBusinessException {
		logger.info("Inside findGroceryByMaxPriceAndSorted {}", this.getClass());
		List<GroceryPriceData> groceryPriceDataList = groceryPriceDataRepository.findByItemNameContaining(itemName);
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = convertToResponseModel(groceryPriceDataList);
		logger.info("Converted entity object to response object in findByItemNameContaining.", this.getClass());
		return groceryPriceDataResponseList;
	}

	/**
	 * Convert entity object list into response object.
	 * 
	 * @param groceryPriceDataList
	 * @return
	 */
	private List<GroceryPriceDataResponse> convertToResponseModel(List<GroceryPriceData> groceryPriceDataList) {
		logger.info("Converting entity object to response object {}", this.getClass());
		List<GroceryPriceDataResponse> groceryPriceDataResponseList = new ArrayList<>();
		groceryPriceDataList.forEach(
				grocery -> groceryPriceDataResponseList.add(modelMapper.map(grocery, GroceryPriceDataResponse.class)));
		return groceryPriceDataResponseList;
	}

}
