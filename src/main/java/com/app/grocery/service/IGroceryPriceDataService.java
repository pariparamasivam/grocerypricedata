package com.app.grocery.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.grocery.exception.ItemNameNotFoundException;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.service.exception.GroceryPriceDataBusinessException;

public interface IGroceryPriceDataService {

	List<GroceryPriceDataResponse> findGroceryByMaxPriceAndSorted() throws GroceryPriceDataBusinessException;
	List<GroceryPriceDataResponse> findByItemName(String itemName) throws GroceryPriceDataBusinessException,ItemNameNotFoundException;
	List<GroceryPriceDataResponse> findByItemNameContaining(String itemName) throws GroceryPriceDataBusinessException, ItemNameNotFoundException;
}
