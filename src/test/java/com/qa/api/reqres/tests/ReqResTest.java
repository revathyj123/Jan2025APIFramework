package com.qa.api.reqres.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResTest extends BaseTest {

	@Test
	public void getUserTest() {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("page", "2");
		Response response = restClient.get(BASE_URL_REQRES, REQRES_ENDPOINT, queryParams, null, AuthType.NO_AUTH,
				ContentType.JSON, null);
		Assert.assertEquals(response.statusCode(), 200);
	}
}
