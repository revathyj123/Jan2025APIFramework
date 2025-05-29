package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 100: GoRest Get User API feature")
@Story("User Story 100: feature gorest API - get User")
public class GetUserTest extends BaseTest {
	
	@Description("Getting all the users")
	@Owner("Revathy Ganesh Kumar")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersTest() {
		
		 //log - ChainTest
		  ChainTestListener.log("log example");

		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN,
				ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void getAllUsersWithQueryParamsTest() {
		Map<String, String> queryParams=new HashMap<String, String>();
		queryParams.put("name","Revathy");
		queryParams.put("status","active");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test(enabled = false)
	public void getASingleUserTest() {
		String userId="7888234";
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"), userId);
	}
}