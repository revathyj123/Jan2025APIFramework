package com.qa.api.utils;

public class StringUtils{

	public static String generateRandomEmailId() {
		String emailId="testapi_"+System.currentTimeMillis()+"@testemail.com";
		return emailId;
	}
}
