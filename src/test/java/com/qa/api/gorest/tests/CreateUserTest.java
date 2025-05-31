package com.qa.api.gorest.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtil;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {

	/**
	 * The below line is commented 
	 * because it supplies same emailId to all the tests which treated as duplicate.
	 * throws Assertion error 422 (Unprocessable Entity) error. 
	 */
	//private String emailId = StringUtils.generateRandomEmailId();

	@DataProvider
	public Object[][] getUserData(){
		return new Object[][] {
			{"Revathy", "female", "active"},
			{"Tester2", "female", "inactive"},
			{"Tester3", "male", "active"},
			{"Tester4", "male", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getExcelUserData() throws IOException{
		return ExcelUtil.readExcelData(AppConstants.CREATE_USER_SHEET_NAME);
	}
	
	@Test(dataProvider="getUserData")
	public void createAUserTest(String name, String gender, String status) {
		//User user = new User(null, name, gender, StringUtils.generateRandomEmailId(), status);
		
		User user = User.builder()
				.name(name)
				.gender(gender)
				.email(StringUtils.generateRandomEmailId())
				.status(status)
				.build();
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		
		System.out.println("createAUserTest Name = "+response.jsonPath().getString("name"));
		System.out.println("createAUserTest ID = "+response.jsonPath().getString("id"));
		
		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertEquals(response.jsonPath().getString("gender"), gender);
		Assert.assertEquals(response.jsonPath().getString("status"), status);

		Assert.assertNotNull(response.jsonPath().getString("id"));
	}

	@Test
	public void createAUserWithJSONStringTest() {
		String userJson = "{\r\n" + "    \"name\": \"RevathyG\",\r\n" + "    \"email\": \"" + StringUtils.generateRandomEmailId() + "\",\r\n"
				+ "    \"gender\": \"female\",\r\n" + "    \"status\": \"active\"\r\n" + "}";
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null,
				AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);
		
		System.out.println("createAUserWithJSONStringTest Name = "+response.jsonPath().getString("name"));
		System.out.println("createAUserWithJSONStringTest ID = "+response.jsonPath().getString("id"));
		
		Assert.assertEquals(response.jsonPath().getString("name"), "RevathyG");
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}

	@Test
	public void createAUserWithFileTest() {

		String rawUserJsonString;
		String updatedUserJsonString;

		try {
			rawUserJsonString = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
			System.out.println("rawUserJsonString === " + rawUserJsonString);
			updatedUserJsonString = rawUserJsonString.trim().replace("{{email}}", StringUtils.generateRandomEmailId());
			System.out.println("updatedUserJsonString ****** "+ updatedUserJsonString);

			Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, updatedUserJsonString, null,
					null, AuthType.BEARER_TOKEN, ContentType.JSON, GOREST_CONFIG_KEY);

			System.out.println("Name = "+response.jsonPath().getString("name"));
			System.out.println("ID = "+response.jsonPath().getString("id"));

			
			//Assert.assertEquals(response.jsonPath().getString("name"), "Revathy_Test");
			//Assert.assertNotNull(response.jsonPath().getString("id"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
