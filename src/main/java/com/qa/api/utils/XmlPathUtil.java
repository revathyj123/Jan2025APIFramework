package com.qa.api.utils;

import java.util.List;
import java.util.Map;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XmlPathUtil {

	private static XmlPath getXMLPath(Response response) {

		String respBody = response.getBody().asString();
		return new XmlPath(respBody);
	}

	public static <T> T read(Response response, String xmlPathExpression) {

		XmlPath xmlPath = getXMLPath(response);
		return xmlPath.get(xmlPathExpression);
	}

	public static <T> List<T> readList(Response response, String xmlPathExpression) {

		XmlPath xmlPath = getXMLPath(response);
		return xmlPath.getList(xmlPathExpression);
	}
	
	public static <T> List<Map<String, T>> readListOfMaps(Response response, String xmlPathExpression) {

		XmlPath xmlPath = getXMLPath(response);
		return xmlPath.getList(xmlPathExpression);
	}

}
