package com.qa.api.amadeus.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AmadeusAPITests extends BaseTest {

	private String accessToken;

	@BeforeMethod
	public void getAccessToken() {

		Response response = restClient.post(BASE_URL_OAUTH2_AMADEUS, OAUTH2_AMADEUS_ENDPOINT,
				ConfigManager.get("granttype"), ConfigManager.get("amadeuxclientid"), ConfigManager.get("amadeuxclientsecret"),
				ContentType.URLENC);
		accessToken = response.jsonPath().getString("access_token");
		System.out.println("accessToken = " + accessToken);
		Assert.assertNotNull(accessToken);
		
		ConfigManager.set(AMADEUS_CONFIG_KEY, accessToken);
		
		System.out.println(ConfigManager.get(AMADEUS_CONFIG_KEY));
	}

	// If there is a Authorization header in request, then accessToken must be
	// appended with Bearer
	
	@Test
	public void getFlightDetailsTest() {
		
		Map<String, String> queryParams=Map.of("origin", "PAR", "maxPrice", "200");
		
		Response response = restClient.get(BASE_URL_OAUTH2_AMADEUS, AMADEUS_FLIGHT_ENDPOINT, queryParams, 
			null, AuthType.BEARER_TOKEN, ContentType.ANY, AMADEUS_CONFIG_KEY);
		
		response.prettyPrint();
	}
}
