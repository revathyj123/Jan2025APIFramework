package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUSerWithDesrialisationTest extends BaseTest {

	private String emailId = StringUtils.generateRandomEmailId();

	@Test
	public void createAUserTest() {

		// 1. Create a User with POJO(Serialisation)
		User user = new User(null, "Revathy", "female", emailId, "active");
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertEquals(response.jsonPath().getString("name"), "Revathy");
		Assert.assertNotNull(response.jsonPath().getString("id"));

		// Fetch the User ID
		String userId = response.jsonPath().getString("id");

		System.out.println("User Id===== " + userId);

		// 2. Get the user with the fetched userid

		Response responseGET = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT + "/" + userId, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);

		User userResponse = JsonUtils.deserialize(responseGET, User.class);
		Assert.assertEquals(userResponse.getName(), user.getName());

		// 3. Get All users

		Response responseGETAll = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(responseGETAll.statusLine().contains("OK"));
		Assert.assertEquals(responseGETAll.statusCode(), 200);

		User[] allUsersResponse = JsonUtils.deserialize(responseGETAll, User[].class);
		for(User e : allUsersResponse) {
			System.out.println("Id = "+e.getId());
			System.out.println("Name = "+e.getName());
			System.out.println("Gender = "+e.getGender());
		}

	}

}
