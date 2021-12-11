package com.app.grocery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.app.grocery.GroceryAppApplication;
import com.app.grocery.entity.GroceryPriceData;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.repository.GroceryPriceDataRepository;
import com.app.grocery.service.impl.GroceryPriceDataServiceImpl;

@SpringBootTest(classes = GroceryAppApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class GroceryPriceDataServiceTests {

	@InjectMocks
	private IGroceryPriceDataService groceryPriceDataService = new GroceryPriceDataServiceImpl();

	@Mock
	private GroceryPriceDataRepository groceryPriceDataRepository;

	@Mock
	ModelMapper modelMapper;

	@Autowired
	private MockMvc mockMvc;

	List<GroceryPriceData> mockList = new ArrayList<>();
	List<GroceryPriceDataResponse> mockListResponse = new ArrayList<>();
	GroceryPriceData groceryPriceData = new GroceryPriceData();
	GroceryPriceDataResponse groceryPriceDataResponse = new GroceryPriceDataResponse();

	/**
	 * Get Entity Mock List for testing.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<GroceryPriceData> getMockList() throws ParseException {
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		List<GroceryPriceData> mockList = Arrays.asList(new GroceryPriceData(1L, "Kashini greens", date01, 10.0));

		return mockList;
	}

	/**
	 * Mock Response object.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public List<GroceryPriceDataResponse> getMockListResponse() throws ParseException {
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(1L, "Kashini greens", date01, 10.0, null));

		return mockList;
	}

	/**
	 * Method : findGroceryByMaxPriceAndSorted, Test -Get item details which has max
	 * price.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getGroceryItemByMaxPriceAndSorted() throws Exception {
		List<GroceryPriceData> mockList = getMockList();
		when(groceryPriceDataRepository.findGroceryByMaxPriceAndSorted()).thenReturn(mockList);
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		GroceryPriceDataResponse g = new GroceryPriceDataResponse(1L, "Kashini greens", date01, 10.0, null);
		when(modelMapper.map(any(), any())).thenReturn(g);
		List<GroceryPriceDataResponse> groceryDataResponses = groceryPriceDataService.findGroceryByMaxPriceAndSorted();
		assertEquals("Kashini greens", groceryDataResponses.get(0).getItemName());
	}

	/**
	 * Method : findByItemNameContaining, Test -Get item details which has max
	 * price.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllGroceryItemsByNameContains() throws Exception {
		List<GroceryPriceData> mockList = getMockList();
		when(groceryPriceDataRepository.findByItemNameContaining(anyString())).thenReturn(mockList);
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		GroceryPriceDataResponse g = new GroceryPriceDataResponse(1L, "Kashini greens", date01, 10.0, null);
		when(modelMapper.map(any(), any())).thenReturn(g);
		List<GroceryPriceDataResponse> groceryDataResponses = groceryPriceDataService.findByItemNameContaining("Kas");
		assertEquals("Kashini greens", groceryDataResponses.get(0).getItemName());
	}

	/**
	 * Method : findByItemName, Test -Get item details which has max price.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllGroceryItemsSortByName() throws Exception {
		List<GroceryPriceData> mockList = getMockList();
		when(groceryPriceDataRepository.findByItemName(anyString())).thenReturn(mockList);
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		GroceryPriceDataResponse g = new GroceryPriceDataResponse(1L, "Kashini greens", date01, 10.0, null);
		when(modelMapper.map(any(), any())).thenReturn(g);
		List<GroceryPriceDataResponse> groceryDataResponses = groceryPriceDataService.findByItemName("Kashini greens");
		assertEquals("Kashini greens", groceryDataResponses.get(0).getItemName());
	}

}
