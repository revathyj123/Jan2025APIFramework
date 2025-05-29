package com.qa.api.circuitxml.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XmlPathUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CircuitAPIXmlTests extends BaseTest {

	@Test
	public void getCircuitXmlTest() {
		Response response = restClient.get(BASE_URL_ERGAST_CIRCUIT, ERGAST_CIRCUIT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY, null);
		
		List<String> nameList = XmlPathUtil.readList(response, "MRData.CircuitTable.Circuit.CircuitName");

		System.out.println(nameList.size());

		for (String e : nameList) {
			System.out.println(e);
			Assert.assertNotNull(e);
		}
		
		System.out.println("***************************************************");

		// Query Based - fetch the locality where circuitId='americas'

		// find - for single element
		// it ==== iterator

		String locality = XmlPathUtil.read(response,"**.find{it.@circuitId=='americas'}.Location.Locality");
		System.out.println("Locality = " + locality);
		Assert.assertEquals(locality, "Austin");

		System.out.println("***************************************************");

		// findAll - for multiple elements

		List<String> localityList = XmlPathUtil.readList(response,"**.findAll{it.@circuitId=='americas'}.Location.Locality");
		System.out.println("List of Locality = " + localityList);

		System.out.println("***************************************************");
	
	}

}
