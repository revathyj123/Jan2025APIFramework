package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties properties = new Properties();
	
	static {
		
		//mvn clean install -Denv=qa/dev/uat/prod/stage
		//mvn clean install -Denv=qa then config_qa.properties
		//mvn clean install - if env is not given then run tcs On QA env by default
		//env - environment variable(System)
		
		String envName = System.getProperty("env", "qa");
		System.out.println("Running test cases on env: "+ envName);
		String fileName = "config_"+envName+".properties";//config_qa.properties
		
		InputStream input= ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
		if(input!=null) {
			try {
				properties.load(input);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}
}
