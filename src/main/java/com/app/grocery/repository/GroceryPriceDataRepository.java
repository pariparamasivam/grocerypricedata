package com.app.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grocery.entity.GroceryPriceData;

@Repository
public interface GroceryPriceDataRepository extends JpaRepository<GroceryPriceData, Long> {

	@Query(value = "select * from grocery_price_data"
			+ "  where (item_name,price ) in (select Item_name ,max(price) as price from GROCERY_PRICE_DATA  group by item_name)"
			+ "order by item_name, price, dateadded", nativeQuery = true)
	List<GroceryPriceData> findGroceryByMaxPriceAndSorted();

	List<GroceryPriceData> findByItemName(String itemName);

	@Query(value = "select * from grocery_price_data"
			+ "  where item_name like %:itemName% and (item_name,price ) in (select Item_name ,max(price) as price from GROCERY_PRICE_DATA  group by item_name)"
			+ "order by item_name, price, dateadded", nativeQuery = true)
	List<GroceryPriceData> findByItemNameContaining(@Param("itemName") String itemName);
}
