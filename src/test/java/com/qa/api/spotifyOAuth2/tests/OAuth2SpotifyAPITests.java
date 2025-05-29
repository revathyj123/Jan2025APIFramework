package com.qa.api.spotifyOAuth2.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAuth2SpotifyAPITests extends BaseTest {

	private String accessToken;

	@BeforeMethod
	public void getAccessToken() {

		Response response = restClient.post(BASE_URL_OAUTH2_SPOTIFY, OAUTH2_SPOTIFY_ENDPOINT,
				ConfigManager.get("granttype"), ConfigManager.get("spotifyclientid"), ConfigManager.get("spotifyclientsecret"),
				ContentType.URLENC);

		response.prettyPrint();
		accessToken = response.jsonPath().getString("access_token");
		System.out.println("accessToken = " + accessToken);

		ConfigManager.set(SPOTIFY_CONFIG_KEY, accessToken);

		System.out.println(ConfigManager.get(SPOTIFY_CONFIG_KEY));

	}

	// If there is a Authorization header in request, then accessToken must be
	// appended with Bearer

	@Test
	public void getAlbumDetailsTest() {
		Response response = restClient.get(BASE_URL_SPOTIFY_ALBUM, SPOTIFY_ALBUM_ENDPOINT, null, null,
				AuthType.BEARER_TOKEN, ContentType.ANY, SPOTIFY_CONFIG_KEY);

		response.prettyPrint();
		Assert.assertEquals(response.statusCode(), 200);

		List<Map<String, Object>> imgUrlheightList = JsonPathValidatorUtil.readListOfMaps(response,
				"$.images[?(@.height==64)].['url','width']");

		System.out.println(imgUrlheightList);

		// $.tracks.items[*].artists[1].name

		List<String> trImArNameList = JsonPathValidatorUtil.readList(response, "$.tracks.items[*].artists[1].name");
		System.out.println(trImArNameList);
	}

	// If there is no Authorization header in request, then accessToken must not be
	// appended with "Bearer"

	@Test
	public void getAlbumDetailsOAuth2APITest() {
		RestAssured.baseURI = "https://api.spotify.com";

		Response response = RestAssured.given().log().all().auth().oauth2(accessToken).when().log().all()
				.get("/v1/albums/4aawyAB9vmqN3uQ7FjRGTy");
		response.prettyPrint();
		Assert.assertEquals(response.statusCode(), 200);
	}

}
