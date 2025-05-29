package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Contacts;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateContactTest extends BaseTest {
	
	@BeforeClass
	public void setContactsApiFlag() {
	    isContactsApiTest = true;  // Enable Contacts API token retrieval
	}

	@Test
	public void updateAContactTest() {

		// 1. Create a Contact using POJO with Lombok

		Contacts contact = Contacts.builder().firstName("TestFN").lastName("TestLN").birthdate("1989-09-09")
				.email(StringUtils.generateRandomEmailId()).phone("1234567890").street1("13 School St").street2("Apt 5")
				.city("Toronto").stateProvince("ON").postalCode("L1L2L3").country("Canada").build();

		System.out.println("Contacts ================"+contact);
		
		Response responsePOST = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		responsePOST.prettyPrint();
		Assert.assertEquals(responsePOST.jsonPath().getString("firstName"), "TestFN");
		Assert.assertNotNull(responsePOST.jsonPath().getString("_id"));

		// Fetch the Contacts ID
		String contactsId = responsePOST.jsonPath().getString("_id");

		System.out.println("contacts Id===== " + contactsId);

		// 2. Get the contacts with the fetched contactsId

		Response responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactsId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("_id"), contactsId);

		// 3. Update the contacts with the fetched contactsId
		contact.setFirstName("Revathy_API");
		contact.setLastName("Ganesh");
		Response responsePUT = restClient.put(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactsId, contact, null,
				null, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertTrue(responsePUT.statusLine().contains("OK"));
		Assert.assertEquals(responsePUT.jsonPath().getString("_id"), contactsId);
		Assert.assertEquals(responsePUT.jsonPath().getString("firstName"), "Revathy_API");
		Assert.assertNotNull(responsePUT.jsonPath().getString("_id"));

		// 4. Get the contact with the fetched contactsId
		responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactsId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("_id"), contactsId);

	}
}
