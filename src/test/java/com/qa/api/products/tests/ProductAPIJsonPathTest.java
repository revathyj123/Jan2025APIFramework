package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIJsonPathTest extends BaseTest {

	@Test
	public void getProductTest() {

		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH,
				ContentType.JSON, CONTACTS_CONFIG_KEY);
		Assert.assertEquals(response.statusCode(), 200);

		List<Number> prices = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].price");
		System.out.println(prices);

		List<Number> ids = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].id");
		System.out.println(ids);

		List<Number> rates = JsonPathValidatorUtil.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println(rates);

		List<Map<String, Object>> idTitleLsit = JsonPathValidatorUtil.readListOfMaps(response, "$..['id','title']");
		System.out.println(idTitleLsit);
		System.out.println("Size of the list =" + idTitleLsit.size());
		for (Map<String, Object> e : idTitleLsit) {
			int id = (int) e.get("id");
			System.out.println("Id == " + id);

			String title = (String) e.get("title");
			System.out.println("Title == " + title);
		}

		// Retrieving three attributes id, title and category
		List<Map<String, Object>> threeAttrLsit = JsonPathValidatorUtil.readListOfMaps(response,
				"$..['id','title', 'category']");
		System.out.println(threeAttrLsit);
		for (Map<String, Object> e : threeAttrLsit) {
			int id = (int) e.get("id");
			System.out.println("Id == " + id);

			String title = (String) e.get("title");
			System.out.println("Title == " + title);

			String category = (String) e.get("category");
			System.out.println("Category == " + category);
		}

		// Conditional based JSON queries

		// $[?(@.category == 'jewelery')].id
		// $[?(@.category == 'jewelery')].title
		// $[?(@.category == 'jewelery')].['id','title']
		// $[?(@.category == 'jewelery')].['id','title','price']
		// $[?(@.category == 'jewelery')].rating.rate

		List<Map<String, Object>> jewIdTitlesList = JsonPathValidatorUtil.readListOfMaps(response,
				"$[?(@.category == 'jewelery')].['id','title']");
		System.out.println(jewIdTitlesList);

		// Fetching single/specific value

		// Getting minimum price
		double minPrice = JsonPathValidatorUtil.read(response,"min($[*].price)");
		System.out.println(minPrice);
	}

}
