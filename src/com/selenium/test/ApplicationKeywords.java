package com.selenium.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ApplicationKeywords {

	public WebDriver driver;
	public static Properties CONFIG;
	
	Keywords common = new Keywords();
	
	ApplicationKeywords(WebDriver driver) throws IOException {		
		this.driver = driver;
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//isaca//config//config.properties");
		ApplicationKeywords.CONFIG= new Properties();
		ApplicationKeywords.CONFIG.load(fs);
	}
	
	public void JoinCommunity(String object,String data) {
		
		String JoinCommunityVal = driver.findElement(By.xpath(object)).getText();
		if (JoinCommunityVal.contains("Join")) {
			common.click(object, data);
			common.switchTopopupWindow(CONFIG.getProperty("emailalert"), data);
			common.elementEnabled(CONFIG.getProperty("leavecomment_input"), data);			
		} else common.click(object, data);	
		
	}
	
}
