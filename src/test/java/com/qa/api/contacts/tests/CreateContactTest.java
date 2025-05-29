package com.qa.api.contacts.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Contacts;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateContactTest extends BaseTest{

	private String emailId = StringUtils.generateRandomEmailId();

	@BeforeClass
	public void setContactsApiFlag() {
	    isContactsApiTest = true;  // Enable Contacts API token retrieval
	}
	
	@Test
	public void createAContactTest() {
		Contacts contacts = new Contacts("TestFN", "TestLN", "1992-02-02", emailId,
				"8005554242", "13 School St.", "Apt. 5", "Toronto", "ON", "B1B1B1", "Canada");
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contacts, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(response.jsonPath().getString("firstName"), "TestFN");
		Assert.assertNotNull(response.jsonPath().getString("_id"));
	}

	@Test
	public void createAContactWithJSONStringTest() {
		String contactsJson = "{\r\n"
				+ "    \"firstName\": \"Revathy\",\r\n"
				+ "    \"lastName\": \"Ganesh\",\r\n"
				+ "    \"birthdate\": \"1989-02-02\",\r\n"
				+ "    \"email\": \""+emailId+"\",\r\n"
				+ "    \"phone\": \"9900099000\",\r\n"
				+ "    \"street1\": \"13 School St.\",\r\n"
				+ "    \"street2\": \"Apt. 5\",\r\n"
				+ "    \"city\": \"Mississauga\",\r\n"
				+ "    \"stateProvince\": \"ON\",\r\n"
				+ "    \"postalCode\": \"A1A1A1\",\r\n"
				+ "    \"country\": \"Canada\"\r\n"
				+ "}";
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contactsJson, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(response.jsonPath().getString("firstName"), "Revathy");
		Assert.assertNotNull(response.jsonPath().getString("_id"));
	}

	@Test
	public void createAContactWithFileTest() {

		String rawContactJsonString;
		String updatedContactJsonString;

		try {
			rawContactJsonString = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/contacts.json")));
			System.out.println("rawContactJsonString === " + rawContactJsonString);
			System.out.println("emailId = " + emailId);

			updatedContactJsonString = rawContactJsonString.trim().replace("{{email}}", emailId);
			System.out.println(updatedContactJsonString);

			Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, updatedContactJsonString, null,
					null, AuthType.BEARER_TOKEN, ContentType.JSON, CONTACTS_CONFIG_KEY);

			Assert.assertEquals(response.jsonPath().getString("firstName"), "ContactsAPI");
			Assert.assertNotNull(response.jsonPath().getString("_id"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
