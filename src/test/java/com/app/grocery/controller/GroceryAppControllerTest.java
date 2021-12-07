package com.app.grocery.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.grocery.GroceryAppApplication;
import com.app.grocery.exception.ExceptionResponse;
import com.app.grocery.exception.ItemNameNotFoundException;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.service.IGroceryPriceDataService;
import com.app.grocery.service.exception.GroceryPriceDataBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroceryAppApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GroceryAppControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IGroceryPriceDataService groceryPriceDataService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	 /**
	  * Test Item Not Found exception for API :/getGroceryItemByMaxPrice
	  * @throws Exception
	  */
	@Test
	public void getGroceryItemByMaxPriceException() throws Exception {
		
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Item Name not found"
						, "404 NOT_FOUND")));
		when(groceryPriceDataService.findByItemNameContaining(anyString())).thenThrow(ItemNameNotFoundException.class);

		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getGroceryItemByMaxPrice")
				.contextPath("/groceryapp").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	} 
	 /**
	  * Test INTERNAL_SERVER_ERROR exception for API :/getGroceryItemByMaxPrice
	  * @throws Exception
	  */
	@Test
	public void getGroceryItemByMaxPriceServerException() throws Exception {
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Internal Server error while getting details"
						, "500 INTERNAL_SERVER_ERROR")));
		when(groceryPriceDataService.findGroceryByMaxPriceAndSorted()).thenThrow(GroceryPriceDataBusinessException.class);

		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getGroceryItemByMaxPrice")
				.contextPath("/groceryapp").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	} 
	
	 /**
	  * Test Item Not Found exception for API :/getAllGroceryItemsSortByName
	  * @throws Exception
	  */
	@Test
	public void getAllGroceryItemsSortByNameException() throws Exception {
		
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Item Name not found"
						, "404 NOT_FOUND")));
		when(groceryPriceDataService.findByItemName(anyString())).thenThrow(ItemNameNotFoundException.class);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsSortByName")
				.contextPath("/groceryapp").param("itemName", "Zn").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	}
	/**
	  * Test INTERNAL_SERVER_ERROR exception for API :/getAllGroceryItemsSortByName
	  * @throws Exception
	  */
	@Test
	public void getAllGroceryItemsSortByNameServerException() throws Exception {
		
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Internal Server error while getting details"
						, "500 INTERNAL_SERVER_ERROR")));
		when(groceryPriceDataService.findByItemName(anyString())).thenThrow(GroceryPriceDataBusinessException.class);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsSortByName")
				.contextPath("/groceryapp").param("itemName", "Zn").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	}

	 /**
	  * Test Item Not Found exception for API :/getAllGroceryItemsByNameContains
	  * @throws Exception
	  */
	@Test
	public void getAllGroceryItemsByNameContainsException() throws Exception {
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Item Name not found"
						, "404 NOT_FOUND")));
		when(groceryPriceDataService.findByItemNameContaining(anyString())).thenThrow(ItemNameNotFoundException.class);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsByNameContains")
				.contextPath("/groceryapp").param("itemName", "Zn").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().isNotFound())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	}
	
	
	 /**
	  * Test INTERNAL_SERVER exception for API :/getAllGroceryItemsByNameContains
	  * @throws Exception
	  */
	@Test
	public void getAllGroceryItemsByNameContainsServerException() throws Exception {
		List<GroceryPriceDataResponse> mockList = Arrays
				.asList(new GroceryPriceDataResponse(null, null, null, null, new ExceptionResponse("Internal Server error while getting details"
						, "500 INTERNAL_SERVER_ERROR")));
		when(groceryPriceDataService.findByItemNameContaining(anyString())).thenThrow(GroceryPriceDataBusinessException.class);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsByNameContains")
				.contextPath("/groceryapp").param("itemName", "Zn").accept(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		String expectedJSON = mapper.writeValueAsString(mockList);
		MvcResult result = mockMvc.perform(request).andExpect(status().is5xxServerError())
				.andReturn();
		JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);
	}
	
	 /**
	  * Get Mock List for testing.
	  * @return
	  * @throws ParseException
	  */
	public List<GroceryPriceDataResponse> getMockList() throws ParseException {
		String date01Str = "01-01-2011";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date01 = formatter.parse(date01Str);
		String date02Str = "20-05-2012";
		Date date02 = formatter.parse(date02Str);
		List<GroceryPriceDataResponse> mockList = Arrays.asList(
				new GroceryPriceDataResponse(1L, "Kashini greens", date01, 10.0, null),
				new GroceryPriceDataResponse(2L, "Kashini greens", date02, 20.0, null));
		
		return mockList;
	}
	
	@Test
	public void getAllGroceryItemsByNameContains() throws Exception {
		List<GroceryPriceDataResponse> mockList = getMockList();
		when(groceryPriceDataService.findByItemNameContaining(anyString())).thenReturn(mockList);

		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsByNameContains")
				.contextPath("/groceryapp").param("itemName", "Th").accept(MediaType.APPLICATION_JSON);
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setPrettyPrinting().create();
		String exceptedJSON = gson.toJson(mockList).replaceAll("}", ",\"exceptionResponse\":null}");
		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(exceptedJSON))
				.andReturn();
		
		JSONAssert.assertEquals(exceptedJSON, result.getResponse().getContentAsString(), false);
	}
	

	@Test
	public void getGroceryItemByMaxPriceAndSorted() throws Exception {
		List<GroceryPriceDataResponse> mockList = getMockList();
		when(groceryPriceDataService.findGroceryByMaxPriceAndSorted()).thenReturn(mockList);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getGroceryItemByMaxPrice")
				.contextPath("/groceryapp").accept(MediaType.APPLICATION_JSON);
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setPrettyPrinting().create();
		String json = gson.toJson(mockList).replaceAll("}", ",\"exceptionResponse\":null}");
		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(json))
				.andReturn();
		JSONAssert.assertEquals(json, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void getAllGroceryItemsSortByName() throws Exception {
		List<GroceryPriceDataResponse> mockList = getMockList();
		when(groceryPriceDataService.findByItemName(anyString())).thenReturn(mockList);
		RequestBuilder request = MockMvcRequestBuilders.get("/groceryapp/v1/api/getAllGroceryItemsSortByName")
				.contextPath("/groceryapp").param("itemName", "Kashini greens").accept(MediaType.APPLICATION_JSON);
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setPrettyPrinting().create();
		String json = gson.toJson(mockList).replaceAll("}", ",\"exceptionResponse\":null}");
		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(json))
				.andReturn();
		JSONAssert.assertEquals(json, result.getResponse().getContentAsString(), true);
	}

}
