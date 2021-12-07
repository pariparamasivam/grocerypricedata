package com.app.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.app.grocery.GroceryAppApplication;
import com.app.grocery.model.GroceryPriceDataResponse;
import com.app.grocery.repository.GroceryPriceDataRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroceryAppApplication.class)
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase
public class GroceryAppServiceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IGroceryPriceDataService groceryPriceDataService;
	
	@MockBean
	private GroceryPriceDataRepository groceryPriceDataRepository;
	
	  @Autowired
	  private WebApplicationContext webApplicationContext;

	  @Before
	  public void setUp() {
	    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

		/**
		 * Test -Get item details which has max price.
		 * @throws Exception
		 */
		@Test
		public void getGroceryItemByMaxPriceAndSorted() throws Exception {
			List<GroceryPriceDataResponse> mockList = getMockList();
			when(groceryPriceDataService.findGroceryByMaxPriceAndSorted()).thenReturn(mockList);
			assertThat(groceryPriceDataService.findGroceryByMaxPriceAndSorted()).isEqualTo(mockList);
		}
		 
	  
		/**
		 * Test- Get item names sorted by search text.
		 * @throws Exception 
		 */
		@Test
		public void getAllGroceryItemsSortByName() throws Exception {
			List<GroceryPriceDataResponse> mockList = getMockList();
			when(groceryPriceDataService.findByItemName(anyString())).thenReturn(mockList);
			assertThat(groceryPriceDataService.findByItemName("Kashini greens")).isEqualTo(mockList);
		}
		 
		/**
		 * Test - Get Item name with max price by search text.
		 * @throws Exception
		 */
		@Test
		public void getAllGroceryItemsByNameContains() throws Exception {
			List<GroceryPriceDataResponse> mockList = getMockList();
			when(groceryPriceDataService.findByItemNameContaining(anyString())).thenReturn(mockList);

			assertThat(groceryPriceDataService.findByItemNameContaining("Ka")).isEqualTo(mockList);

		}
		 
}
