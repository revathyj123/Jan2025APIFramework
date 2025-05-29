package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetContactsTest extends BaseTest{

	/*@BeforeMethod
	public void getToken() {
		ContactsCredentials loginContacts = ContactsCredentials.builder()
				.email("jrevathy82@gmail.com")
				.password("Ganesha@1983")
				.build();
		Response response=restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, loginContacts, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
		contactsTokenId = response.jsonPath().getString("token");
		System.out.println("Token = " + contactsTokenId);
		ConfigManager.set("bearertoken", contactsTokenId);
	}*/
	
	@BeforeClass
	public void setContactsApiFlag() {
	    isContactsApiTest = true;  // Enable Contacts API token retrieval
	}
	
	@Test
	public void getAllContactsTest(){
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void getAContactTest(){
		String contactsId="67ad047f8c4f700013758e88";
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+contactsId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
