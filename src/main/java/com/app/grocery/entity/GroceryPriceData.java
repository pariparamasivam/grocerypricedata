package com.app.grocery.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="grocery_price_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryPriceData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 //@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name="item_name")
	private String itemName;
	
	@Column(name="dateadded")
	private Date dateadded;
	
	@Column(name="price")
	private Double price;
	}
