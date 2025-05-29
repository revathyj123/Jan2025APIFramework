package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//This class is just for demo of Lombok class - used in CreateAContactWithLombokTest.java

@Builder
@Data //generates getter and setter methods for the private variables 
@AllArgsConstructor //Generates an all arg constructor
@NoArgsConstructor //Generates No Arg constructor

public class Contacts {
	private String firstName;
	private String lastName;
	private String birthdate;
	private String email;
	private String phone;
	private String street1;
	private String street2;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String country;
}
