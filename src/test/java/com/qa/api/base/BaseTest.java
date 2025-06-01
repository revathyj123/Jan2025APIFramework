package com.qa.api.base;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Listeners;

import com.aventstack.chaintest.plugins.ChainTestListener;

import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//@Listeners(ChainTestListener.class)
public class BaseTest {

	protected RestClient restClient;// to inherit in the child class

	protected final String GOREST_CONFIG_KEY = "gorestbearertoken";
	protected final String CONTACTS_CONFIG_KEY = "contactsbearertoken";
	protected final String AMADEUS_CONFIG_KEY = "amadeuxbearertoken";
	protected final String SPOTIFY_CONFIG_KEY = "spotifybearertoken";

	protected boolean isContactsApiTest = false;// Flag to retrieve contacts api token
	protected String contactsTokenId;// Contacts token
	protected String goRestTokenId;// GoRest User token

	// *****API Base URLs - All APIs baseUrls will be stored here and can be
	// reused****//

	protected static String BASE_URL_GOREST = null;	
	protected static String BASE_URL_CONTACTS = null;
	
	protected static String BASE_URL_PRODUCTS = null;
	protected static String BASE_URL_SPOTIFY_ALBUM = null;
	protected static String BASE_URL_ERGAST_CIRCUIT = null;
	protected static String BASE_URL_REQRES = null;

	protected static String BASE_URL_BASIC_AUTH = null;
	protected static String BASE_URL_OAUTH2_AMADEUS = null;
	protected static String BASE_URL_OAUTH2_SPOTIFY = null;

	// *************API Endpoint URLs*****************//

	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
	protected final static String CONTACTS_ENDPOINT = "/contacts";
	protected final static String PRODUCTS_ENDPOINT = "/products";
	protected final static String AMADEUS_FLIGHT_ENDPOINT = "/v1/shopping/flight-destinations";
	protected final static String SPOTIFY_ALBUM_ENDPOINT = "/v1/albums/4aawyAB9vmqN3uQ7FjRGTy";
	protected final static String ERGAST_CIRCUIT_ENDPOINT = "/api/f1/2017/circuits.xml";

	protected final static String REQRES_ENDPOINT = "/api/users";
	protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected final static String OAUTH2_AMADEUS_ENDPOINT = "/v1/security/oauth2/token";
	protected final static String OAUTH2_SPOTIFY_ENDPOINT = "/api/token";

	@BeforeSuite
	public void initSetUp() {
		RestAssured.filters(new AllureRestAssured());
		BASE_URL_GOREST = ConfigManager.get("baseurl.gorest").trim();
		BASE_URL_CONTACTS = ConfigManager.get("baseurl.contacts").trim();
		BASE_URL_PRODUCTS = ConfigManager.get("baseurl.products").trim();
		BASE_URL_SPOTIFY_ALBUM = ConfigManager.get("baseurl.spotify").trim();
		BASE_URL_ERGAST_CIRCUIT = ConfigManager.get("baseurl.ergast").trim();
		BASE_URL_REQRES = ConfigManager.get("baseurl.reqres").trim();
		BASE_URL_BASIC_AUTH = ConfigManager.get("baseurl.basicauth").trim();
		BASE_URL_OAUTH2_AMADEUS = ConfigManager.get("baseurl.amadeus").trim();
		BASE_URL_OAUTH2_SPOTIFY = ConfigManager.get("baseurl.oauthspotify").trim();
	}

	@BeforeTest
	public void setToken() {
		goRestTokenId = "0b1b6b285e932ba5d7d1e77eae48c27960e81bf6c2c46a5d82ef99b148a4025b";
		ConfigManager.set(GOREST_CONFIG_KEY, goRestTokenId);
	}

	@BeforeMethod
	public void getToken() {

		if (isContactsApiTest) { // Only execute when running Contacts API tests
			ContactsCredentials loginContacts = ContactsCredentials.builder().email("jrevathy82@gmail.com")
					.password("Ganesha@1983").build();

			Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, loginContacts, null, null,
					AuthType.NO_AUTH, ContentType.JSON, CONTACTS_CONFIG_KEY);

			Assert.assertEquals(response.getStatusCode(), 200);

			contactsTokenId = response.jsonPath().getString("token");
			System.out.println("Token = " + contactsTokenId);

			// log -- ChainTest log
			ChainTestListener.log("contactsTokenId===== " + contactsTokenId);

			ConfigManager.set(CONTACTS_CONFIG_KEY, contactsTokenId);
		}
	}

	@BeforeClass
	public void setUp() {
		restClient = new RestClient();
	}
}