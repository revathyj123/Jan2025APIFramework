package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data //generates getter and setter methods for the private variables 
@AllArgsConstructor //Generates an all arg constructor
@NoArgsConstructor //Generates No Arg constructor
public class Product {

	private Integer id;
	private String title;
	private Double price;
	private String description;
	private String category;
	private String image;
	private Rating rating;
	
	@Builder
	@Data  
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Rating{
		private Double rate;
		private Integer count;
	}
}
