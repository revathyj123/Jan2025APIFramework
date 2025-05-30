package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {

	@Test
	public void UpdateAUserTest() {
		// User user=new User("Revathy", "female", "Revatest@testapi.com", "active");

		// 1. Create a User using POJO with Lombok
		
		User user = User.builder()
				.name("Revathy")
				.gender("female")
				.email(StringUtils.generateRandomEmailId())
				.status("active")
				.build();

		Response responsePOST = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Revathy");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));

		// Fetch the User ID
		String userId = responsePOST.jsonPath().getString("id");

		System.out.println("User Id===== " + userId);

		// log -- ChainTest log
		ChainTestListener.log("User Id===== " + userId);

		// 2. Get the user with the fetched userid

		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);

		// 3. Update the user with the fetched userid
		user.setName("Revathy_API");
		user.setStatus("inactive");
		Response responsePUT = restClient.put(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(responsePUT.statusLine().contains("OK"));
		Assert.assertEquals(responsePUT.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePUT.jsonPath().getString("name"), "Revathy_API");
		Assert.assertNotNull(responsePUT.jsonPath().getString("id"));

		// 4. Get the user with the fetched userid
		responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);

	}
}
