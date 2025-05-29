package com.qa.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//This class is for login contacts api - used in com.qa.api.contacts.tests

@Builder
@Data //generates getter and setter methods for the private variables 
@AllArgsConstructor //Generates an all arg constructor
@NoArgsConstructor //Generates No Arg constructor
public class ContactsCredentials {

	private String email;
	private String password;

}