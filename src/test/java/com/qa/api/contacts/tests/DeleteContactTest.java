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

public class DeleteContactTest extends BaseTest {

	@BeforeClass
	public void setContactsApiFlag() {
	    isContactsApiTest = true;  // Enable Contacts API token retrieval
	}
	
	@Test
	public void deleteAContactTest() {

		// 1. Create a Contact using POJO with Lombok

		Contacts contact = Contacts.builder().firstName("TestFN").lastName("TestLN").birthdate("1989-09-09")
				.email(StringUtils.generateRandomEmailId()).phone("9900089000").street1("13 School St").street2("Apt 5")
				.city("Toronto").stateProvince("ON").postalCode("L1L2L3").country("Canada").build();

		Response responsePOST = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contact, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
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

		// 3. Delete the contacts with the fetched contactsId
		Response responseDelete = restClient.delete(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactsId, null,
				null, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(responseDelete.statusCode(), 200);
		Assert.assertTrue(responseDelete.statusLine().contains("OK"));
		Assert.assertTrue(responseDelete.asString().contains("Contact deleted"));


		// 4. Get the contact with the fetched contactsId
		responseGET = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT + "/" + contactsId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(responseGET.statusCode(), 404);
		Assert.assertTrue(responseGET.statusLine().contains("Not Found"));
	}
}