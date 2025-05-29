package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.response.Response;

public class JsonPathValidatorUtil {

	public static String getJsonResponseAsString(Response response) {

		return response.getBody().asString();
	}

	/**
	 * 
	 * @param <T> - Any Type
	 * @param response
	 * @param jsonPath
	 * @return
	 */
	public static <T> T read(Response response, String jsonPath) {

		ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));

		return ctx.read(jsonPath);
	}

	public static <T> T readList(Response response, String jsonPath) {

		ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));

		return ctx.read(jsonPath);

	}

	public static <T> T readListOfMaps(Response response, String jsonPath) {

		ReadContext ctx = JsonPath.parse(getJsonResponseAsString(response));

		return ctx.read(jsonPath);
	}
}
