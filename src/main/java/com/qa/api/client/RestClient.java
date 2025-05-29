package com.qa.api.client;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIException;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {

	// define Response Specification

	ResponseSpecification responseSpec200 = expect().statusCode(200);
	ResponseSpecification responseSpec201 = expect().statusCode(201);
	ResponseSpecification responseSpec200Or204 = expect().statusCode(anyOf(equalTo(200), equalTo(204)));
	ResponseSpecification responseSpec401 = expect().statusCode(401);
	ResponseSpecification responseSpec200Or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	ResponseSpecification responseSpec200Or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));

	private RequestSpecification setUpRequest(String baseUrl, AuthType authType, ContentType contentType,
			String configKey) {

		RequestSpecification request = RestAssured.given().log().all().baseUri(baseUrl).contentType(contentType)
				.accept(contentType);

		// log -- ChainTest log
		ChainTestListener.log("baseUrl===== " + baseUrl);

		switch (authType) {
		case NO_AUTH:
			System.out.println("Auth is not required");
			break;
		case BASIC_AUTH:
			request.headers("Authorization", "Basic " + generateBasicAuthToken());
			System.out.println("");
			break;
		case BEARER_TOKEN:
			request.headers("Authorization", "Bearer " + ConfigManager.get(configKey));
			System.out.println("");
			break;
		case API_KEY:
			request.headers("x-api-key", "Api key");
			System.out.println("");
			break;
		// OAuth2 is not needed as its associated with "Bearer" token, so case
		// BEARER_TOKEN is enough
		default:
			System.out.println("This Auth is not supported..Please supply the right Auth type.");
			throw new APIException("Invalid Auth");
		}
		return request;
	}

	private String generateBasicAuthToken() {
		String credentials = ConfigManager.get("basicauthusername") + ":" + ConfigManager.get("basicauthpassword");
		// "admin":admin --> "YWRtaW46YWRtaW4=" (Base64 encode value)
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams,
			Map<String, String> pathParams) {
		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		if (pathParams != null) {
			request.queryParams(pathParams);
		}
	}

	// CRUD Operation:

	// Get: Customized method created by tester.
	// This method will be called by TestNG

	/**
	 * This method is used to call GET APIs
	 * 
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @returns the GET API call response
	 */
	@Step("Calling GET api with base url: {0} {1}")
	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType, String configKey) {

		RequestSpecification request = setUpRequest(baseUrl, authType, contentType, configKey);

		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200Or404).extract().response();
		response.prettyPrint();
		return response;
	}

	// POST
	/**
	 * This method is used to call POST APIs
	 * 
	 * @param <T>         Any type of Body for post call
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType, String configKey) {

		RequestSpecification request = setUpRequest(baseUrl, authType, contentType, configKey);
		applyParams(request, queryParams, pathParams);
		Response response = request.body(body).post(endPoint).then().spec(responseSpec200Or201).extract().response();
		response.prettyPrint();
		return response;
	}

	// POST
	/**
	 * This post method is overloaded and used to call POST APIs for OAuth2
	 * AccessToken
	 * 
	 * @param baseUrl
	 * @param endPoint
	 * @param grantType
	 * @param ClientId
	 * @param clientSecret
	 * @param contentType
	 * @return
	 */
	public Response post(String baseUrl, String endPoint, String grantType, String clientId, String clientSecret,
			ContentType contentType) {

		Response response = RestAssured.given().contentType(contentType).formParam("grant_type", grantType)
				.formParam("client_id", clientId).formParam("client_secret", clientSecret).when()
				.post(baseUrl + endPoint);
		response.prettyPrint();
		return response;
	}

	public <T> Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType, String configKey) {

		RequestSpecification request = setUpRequest(baseUrl, authType, contentType, configKey);
		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * 
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */

	public <T> Response patch(String baseUrl, String endPoint, T body, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType, String configKey) {

		RequestSpecification request = setUpRequest(baseUrl, authType, contentType, configKey);
		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
	}

	/**
	 * 
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public Response delete(String baseUrl, String endPoint, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType, String configKey) {

		RequestSpecification request = setUpRequest(baseUrl, authType, contentType, configKey);
		applyParams(request, queryParams, pathParams);

		Response response = request.delete(endPoint).then().spec(responseSpec200Or204).extract().response();
		response.prettyPrint();
		return response;
	}
}
