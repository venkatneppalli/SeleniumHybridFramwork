package com.selenium.test;
import static com.selenium.test.DriverScript.APP_LOGS;
import static com.selenium.test.DriverScript.CONFIG;
import static com.selenium.test.DriverScript.OR;
import static com.selenium.test.DriverScript.currentTestCaseName;
import static com.selenium.test.DriverScript.currentTestDataSetID;
import static com.selenium.test.DriverScript.currentTestSuiteXLS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.xls.read.Xls_Reader;
public class Keywords {
	
	public WebDriver driver;
	//WebDriver driver=null;
	

	public void CloseProcess(String ProcessName) throws IOException, InterruptedException {
		
		Process p = Runtime.getRuntime().exec("tasklist");		
		Runtime.getRuntime().exec("taskkill /IM " +ProcessName+ " /f");
		Thread.sleep(3000);
	}
	
	public String openBrowser(String object,String data) throws IOException, InterruptedException{		
			
		Process p = Runtime.getRuntime().exec("tasklist");
		
		if(data.equals("Chrome"))
		{
			APP_LOGS.debug("Opening Chrome browser");
			//Kill the previous instances of Chrome Driver			
		
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\venkateswararao.n\\Desktop\\Browser_Drivers\\chromedriver_win32\\chromedriver.exe");
			CloseProcess("chromedriver.exe");
			CloseProcess("chrome.exe");
			
			
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			
		}
		
		if(data.equals("Mozilla"))
		{
			APP_LOGS.debug("Opening Firefox browser");
			//Kill the previous instances of Firefox Driver
			CloseProcess("firefox.exe");
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\venkateswararao.n\\Desktop\\Browser_Drivers\\geckodriver-v0.15.0-win32\\geckodriver.exe");
			
			//FirefoxProfile profile = new FirefoxProfile();
			//profile.setAcceptUntrustedCertificates(true);			
			//profile.setEnableNativeEvents(true);
			//profile.addExtension(file);
			//profile.addExtension(file1);
			//profile.setPreference("extensions.firebug.curr
			System.out.println("came here..");
			driver=new FirefoxDriver();
			System.out.println("came after..");
			
			driver.manage().window().maximize();
		}
		
		if(data.equals("IE"))
		{
			APP_LOGS.debug("Opening IE browser");
			//Kill the previous instances of IE Driver			
			CloseProcess("IEDriverServer.exe");
			CloseProcess("iexplore.exe");
			
			System.setProperty("webdriver.ie.driver", "C:\\Users\\venkateswararao.n\\Desktop\\Browser_Drivers\\IEDriverServer_Win32_3.3.0\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();
			/*
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.focus();");
			js.executeScript("window.resizeTo(window.screen.availWidth,window.screen.availHeight);");
			js.executeScript("if(window.screen) { window.moveTo(0,0); window.resizeTo(window.screen.availWidth,window.screen.availHeight); }");
			//js.executeScript("if(window.screen) { window.moveTo(0,0); window.resizeTo(400,400); }");
			js.executeScript("window.focus();");
			*/
		}
		
		long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;
	}
	
	
	
	/*
	
	public WebDriver openBrowser(String object, String data) throws IOException {
		WebDriver driver = null;
		if (data.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "C:\\jars\\IEDriverServer_Win32_2.31.0\\IEDriverServer.exe");
			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setJavascriptEnabled(true);
			capability.setCapability("enableNativeEvents", true);
			
			driver = new InternetExplorerDriver(capability);
			//driver = new InternetExplorerDriver();
		} else if (data.equalsIgnoreCase("Mozilla")) {
			File file = new File("C:\\SeleniumScripts\\SeleniumJarFiles\\firebug-1.11.2-fx.xpi");
			//File file1 = new File("C:\\SeleniumScripts\\SeleniumJarFiles\\firepath-0.9.7-fx.xpi");
		    
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);			
			profile.setEnableNativeEvents(true);
			profile.addExtension(file);
			//profile.addExtension(file1);
			profile.setPreference("extensions.firebug.currentVersion", "1.11.2");
			//driver = new FirefoxDriver();
			driver = new FirefoxDriver(profile);
		} else if (data.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\jars\\ChromeDriver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		return driver;
	}
	
	8*/
	public String QuitDriver(String object,String data) {
		APP_LOGS.debug("============================END OF TEST CASES EXECUTION=========================");
		driver.quit();
		return Constants.KEYWORD_PASS;
	}
		
	public String navigate(String object,String data){		
		APP_LOGS.debug("Navigating to URL" + data);
		try{
		//driver.get(data);
		driver.navigate().to(data);
		//MODIFIED
		return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		//return Constants.KEYWORD_PASS;
	}
	
	public String clickLink(String object,String data){
        //APP_LOGS.debug("Clicking on link ");
        try{
        String objText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
        driver.findElement(By.xpath(OR.getProperty(object))).click();
        APP_LOGS.debug("Link:"+ objText + " is clicked.");
        //MODIFIED
        return Constants.KEYWORD_PASS;
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }
     
		//return Constants.KEYWORD_PASS;
	}
	
	
	public String clickLink_linkText(String object,String data){
        //APP_LOGS.debug("Clicking on link ");
        driver.findElement(By.linkText(OR.getProperty(object))).click();
     
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public  String verifyLinkText(String object,String data){
        APP_LOGS.debug("Verifying link Text");
        try{
        	//String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
        	
        	
        	List<WebElement> list = driver.findElements(By.tagName(OR.getProperty(object)));
    		
    		for(int i=0;i<list.size();i++){
    			if (list.get(i).getText().equals(data)==true) 
    			{
    				return Constants.KEYWORD_FAIL+" -- Link appear on Quick Links";
    				//break;
    								
    			}
    			}
    		
        	//String actual=driver.findElement(By.tagName(OR.getProperty(object))).getText();
        	//String expected=data;
    		
    		}catch(Exception e){
    		
    			return Constants.KEYWORD_FAIL+" -- Not able to verify Link text"+e.getMessage();
        }
        
        return Constants.KEYWORD_PASS;
        
        }
	
	public  String clickButton(String object,String data){
        //APP_LOGS.debug("Clicking on Button");
        try{
            
        	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        	String objText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
        	if (objText.equals("")) objText= driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
        	driver.findElement(By.xpath(OR.getProperty(object))).click();
        	APP_LOGS.debug("Button:" + objText + " is clicked.");
            
            }catch(Exception e){
            	
    			return Constants.KEYWORD_FAIL+" -- Unable to click on Button"+e.getMessage();
    			
            }
        
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String verifyButtonText(String object,String data){
		APP_LOGS.debug("Verifying the button text for " + data);
		try{
		String actual=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
    	String expected=data;

    	if(actual.equals(expected))
    		return Constants.KEYWORD_PASS;
    	else
    		return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			if(!data.equals(Constants.RANDOM_VALUE)){
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			}else{
				// logic to find a random value in list
				WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				String selectedVal=droplist_cotents.get(index).getText();
				
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(selectedVal);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}
		
		return Constants.KEYWORD_PASS;	
	}
	
	public String verifyAllListElements(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list");
	try{	
		WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
		List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
		
		// extract the expected values from OR. properties
		String temp=data;
		String allElements[]=temp.split(",");
		// check if size of array == size if list
		if(allElements.length != droplist_cotents.size())
			return Constants.KEYWORD_FAIL +"- size of lists do not match";	
		
		for(int i=0;i<droplist_cotents.size();i++){
			if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
			}
		}
	}catch(Exception e){
		return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

	}
		
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
				}
			
			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}
	
	public  String selectRadio(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(String object, String data){
		
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			APP_LOGS.debug("Verifying the Radio Selected:" + data);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			
			if(checked!=null)
				
				return Constants.KEYWORD_FAIL+"- Radio not selected";
							
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	
	public  String checkCheckBox(String object,String data){
		APP_LOGS.debug("Checking checkbox");
		try{
			// true or null
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbo";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public String unCheckCheckBox(String object,String data){
		APP_LOGS.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}
		
		
	}
	
	
	public String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
	    	String expected=data;

	    	if(actual.equals(expected))
	    		return Constants.KEYWORD_PASS;
	    	else
	    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}

	
	
	public  String writeInInput(String object,String data){
		//APP_LOGS.debug("Writing in text box");
		
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			APP_LOGS.debug("TextBox: " + object + " is set with Data:" + data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to set Data in the TextBox "+e.getMessage();
			
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyTextinInput(String object,String data){
       APP_LOGS.debug("Verifying the text in input box");
       try{
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}
	
	public  String clickImage(){
	       APP_LOGS.debug("Clicking the image");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(){
	       APP_LOGS.debug("Verifying inage filename");
			
			return Constants.KEYWORD_PASS;
	}
	
	
	
	
	public  String verifyTitle(String object, String data){
	       APP_LOGS.debug("Verifying title to be displayed as:"+data);
	       try{
	    	  
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle=data;
	    	   if(actualTitle.contains(expectedTitle))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	
	public String exist(String object,String data){
	       APP_LOGS.debug("Checking existance of element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object)));
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Object doest not exist";
			  }
	       
	       return Constants.KEYWORD_PASS;


	       
	}
	
	public String WaitTillObjectExist(String object, String data) throws InterruptedException {
		try {	
		WebElement myDynamicElement = (new WebDriverWait(driver, (int)(Double.parseDouble(data))))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(object))));
		Dimension ElementD = myDynamicElement.getSize();
		//System.out.println("Element Height is " + ElementD.height);
		while(ElementD.height==0 && myDynamicElement.isDisplayed()) {
			ElementD = myDynamicElement.getSize();
			Thread.sleep(2000);
		}
		Thread.sleep(3000);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String click(String object,String data){
	       //APP_LOGS.debug("Clicking on any element");
	       try{
	    	   String objectText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
	    	   if (objectText.equals("")) objectText = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
	    	   driver.findElement(By.xpath(OR.getProperty(object))).click();
	    	   
	    	   if (object.contains("a")) APP_LOGS.debug("Link:" + objectText + " is clicked.");
	    	   else APP_LOGS.debug("Element:" + objectText + " is clicked.");   	   
	    	   
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Not able to click " + object;
			  }
			return Constants.KEYWORD_PASS;
	}
	
	public  String synchronize(String object,String data){
		APP_LOGS.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				Thread.sleep(1000L);
				start++;
			}else{
				break;
			}
		 }
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBrowser(String object, String data){
		
		try{
			Process p = Runtime.getRuntime().exec("tasklist");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			
			while ((line = in.readLine()) != null) {
				if (line.contains("chrome") && data.toLowerCase().contains("chrome")) {	
						APP_LOGS.debug("Closing Chrome browser");
						CloseProcess("chromedriver.exe");
						CloseProcess("chrome.exe");
						break; }
				else
					if(line.contains("iexplore") && data.toLowerCase().contains("ie")) {
						APP_LOGS.debug("Closing IE browser");
						CloseProcess("IEDriverServer.exe");
						CloseProcess("iexplore.exe");
						break;
					}
				else
					if(line.contains("firefox") && data.toLowerCase().contains("mozilla")) {
						APP_LOGS.debug("Closing Firefox browser");
						CloseProcess("firefox.exe");
						break;
					}
				}
			
			//driver.close();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		long time = (long)Double.parseDouble(object);
		Thread.sleep(time*1000L);
		return Constants.KEYWORD_PASS;
	}
	
	//iframe handlingo
	
	public String switchToiframe(String object,String data){

		APP_LOGS.debug("Switching to IFrame:" + object);
        try{
            driver.switchTo().frame(OR.getProperty(object));
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
            }catch(Exception e){
            	
    	    	return Constants.KEYWORD_FAIL+" -- Not able to switch to iframe"+e.getMessage();
            }
        return Constants.KEYWORD_PASS;
	} 

	public String defaultiframe(String object,String data){

		APP_LOGS.debug("Switching back to Default Content");
        try{
            
        	driver.switchTo().defaultContent();
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
                    	        	
            }catch(Exception e){
    	
            	return Constants.KEYWORD_FAIL+" -- Not able to change to default content"+e.getMessage();
            	
            }
        
        return Constants.KEYWORD_PASS;
		
	} 
	
	public String getCurrentURL(String object,String data){

		APP_LOGS.debug("default content");
        try{
            
        	driver.getCurrentUrl();
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
                    	        	
            }catch(Exception e){
            	return Constants.KEYWORD_FAIL+" -- Not able to get current URL"+e.getMessage();
            }
        return Constants.KEYWORD_PASS;
	} 
	
	public String switchTopopupWindow(String object,String data){

		APP_LOGS.debug("Popup window");
        try{
            
        	
        	Set<String> winid= driver.getWindowHandles();
    		
    		Iterator<String> itr = winid.iterator();
    		
    		//click on Exciting New Change - E-mail Enabled Alerts!
    		driver.findElement(By.xpath(OR.getProperty(object))).click();
    		driver.manage().window().maximize();
    				
    		winid = driver.getWindowHandles();
    		itr= winid.iterator();
    		 
    		String mwinid =itr.next();
    		String cwinid =itr.next();
    		
    		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    		
    		driver.switchTo().window(cwinid);
    		
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
    		driver.navigate().to(data);
                    	        	
            }catch(Exception e){
            	return Constants.KEYWORD_FAIL+" -- Not able switch to popup window"+e.getMessage();
            }
        return Constants.KEYWORD_PASS;
	} 
	
	public String elementEnabled(String object,String data){

		APP_LOGS.debug("Element is enabled");
        try{
            
        	
        	boolean txtbody=driver.findElement(By.xpath(OR.getProperty(object))).isEnabled();
        	
        	if(txtbody==true){
        		
        		return Constants.KEYWORD_PASS;
        		
        	}
        	//driver.findElement(By.xpath(OR.getProperty(object))).click();
                    	        	
            }catch(Exception e){
            	return Constants.KEYWORD_FAIL+" -- Element not enabled"+e.getMessage();
            }
        
        return Constants.KEYWORD_PASS;
	} 
	
	
	public String verifyEditLink(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("style");
	    	//String actualStr=actual.toLowerCase();
			String expected="display: none;";

	    	//if(actual.equals(expected)){
	    	if(actual.contains(expected)){	
	    		return Constants.KEYWORD_PASS;
	    	}
	    	else{
	    		
	    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- ";
	    	}
	    		
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}

	
	public String verifyQuickLinksText(String object, String data){
		APP_LOGS.debug("Verifying the Please login to view Your Quick Links text");
					
		try{
			
			List<WebElement> LoginElementsCheck = driver.findElements(By.xpath(object));
			String LoginClassValue = "";
			
			for(int ElementIterate = 0; ElementIterate<LoginElementsCheck.size();ElementIterate++) 
			{
				LoginClassValue = LoginElementsCheck.get(ElementIterate).getAttribute("class");
				if (LoginClassValue.contains("hide"))
					//System.out.println("Please login to View Your Quick Links not found.");
					return Constants.KEYWORD_PASS;
				else
					return Constants.KEYWORD_FAIL+"Please login to View Your Quick Links exists";
			}	
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		return Constants.KEYWORD_PASS;
}


	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
		
	// not a keyword
	
	
	public String clickOnjoinCommunity(String object,String data){
        APP_LOGS.debug("Clicking on Join This Community link");
        
        //object = OR.getProperty(object);
        try{
        	
    		String objText=driver.findElement(By.xpath(OR.getProperty(object))).getText();
    		System.out.println(objText.contains("JOIN THIS COMMUNITY"));
    		
    		if(objText.contains("JOIN THIS COMMUNITY")){
    			//click on Join community
    			driver.findElement(By.xpath(object)).click();
    		}else{
    			
    			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    			//click on leave community
    			driver.findElement(By.xpath(OR.getProperty(object))).click();
    			driver.navigate().to("http://www.isaca.org/Groups/Professional-English/zos-os390/Pages/Overview.aspx");
    			//click on Join community
    			driver.findElement(By.xpath(OR.getProperty(object))).click();
    		}
        	APP_LOGS.debug("Link:"+ objText + " is clicked.");
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }
     	return Constants.KEYWORD_PASS;
	}
	
	
	public void captureScreenshot1(String filename, String keyword_execution_result) throws IOException{
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	public void CaptureScreenshot() throws IOException{
		
		String filename = DriverScript.currentTestSuite+"_"+DriverScript.currentTestCaseName+"_TS"+DriverScript.currentTestStepID+"_"+(DriverScript.currentTestDataSetID-1);
		
		if ((!DriverScript.currentKeyword.equals("closeBrowser")) && (!DriverScript.currentKeyword.equals("QuitDriver")) && (!DriverScript.currentKeyword.equals("openBrowser")) ) {
			
			
			String TakeSnapshot = DriverScript.currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, Constants.TAKE_SNAPSHOT, DriverScript.currentTestDataSetID);
			if (TakeSnapshot.equals("Y")) {
				
				String TakeSnapshotOnPass = CONFIG.getProperty("TakeScreenshotOnPass");
				String TakeSnapshotOnFail = CONFIG.getProperty("TakeScreenshotOnFail");	
				
				if ((TakeSnapshotOnPass.equals("Y")) && DriverScript.keyword_execution_result.equals("PASS")) {
					File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".png"));
				} else if((TakeSnapshotOnFail.equals("Y")) && DriverScript.keyword_execution_result.equals("FAIL")) {
					File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".png"));
				}
					
			}
			
			
			//how do we call
			// what will be the file name
		}
		
	}
	
	public String getUniqueID(boolean blnIncludeMills) {
		Calendar now = Calendar.getInstance();

		String resultStr = "";
		if (blnIncludeMills) {
			resultStr = String.format("%4d%02d%02d%02d%02d%02d_%03d",
					now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
					now.get(Calendar.DATE), now.get(Calendar.HOUR_OF_DAY),
					now.get(Calendar.MINUTE), now.get(Calendar.SECOND),
					now.get(Calendar.MILLISECOND));
		} else {
			resultStr = String.format("%4d%02d%02d%02d%02d%02d",
					now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
					now.get(Calendar.DATE), now.get(Calendar.HOUR_OF_DAY),
					now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
		}
		return resultStr;
	}
	
	public String GetSheetRowVal(String FilePath, String SheetName,int RowNum, int ColumnNum)throws Exception {
		//String FilePath = RelativePath + FileName + ".xls";
		FileInputStream oFile = new FileInputStream(FilePath);
		XSSFWorkbook objWB = new XSSFWorkbook(oFile);
		XSSFSheet objSH = objWB.getSheet(SheetName);
		String CellValue=null;
		try {
			CellValue = objSH.getRow(RowNum).getCell(ColumnNum).getStringCellValue();
		}catch(NullPointerException e){			
			CellValue = "";		
		} catch(IllegalStateException e) {
			 if (e.getMessage().contains("numeric")) {
				 int intCellValue = (int)objSH.getRow(RowNum).getCell(ColumnNum).getNumericCellValue();
				 CellValue = Integer.toString(intCellValue);
			 }		
		}
		return CellValue;
	}
	
	public void DeleteResultsColumn(String FilePath, String SheetName) throws Exception {
		
		FileInputStream oFile = new FileInputStream(FilePath);
		XSSFWorkbook objWB = new XSSFWorkbook(oFile);
		XSSFSheet objSH = objWB.getSheet(SheetName);
		XSSFRow oRow = objSH.getRow(0);
		
		int LastCellValue = oRow.getLastCellNum();
		int ColumnNumber = 0;
		
		String strCellValue;
		int RowIterate = 0;
		int ColumnIterate = 0;
		
		try {
		
		for(ColumnIterate = 0; ColumnIterate<LastCellValue;ColumnIterate++){
			String ActualRowHeader = objSH.getRow(0).getCell(ColumnIterate).getStringCellValue();
			if (ActualRowHeader.toLowerCase().contains("result")) {
				int LastRowNumber = objSH.getLastRowNum();
				for(RowIterate = 0;RowIterate <=LastRowNumber; RowIterate++) {					
					XSSFRow Row = objSH.getRow(RowIterate);
					if (Row!=null) { XSSFCell oCell = objSH.getRow(RowIterate).getCell(ColumnIterate);
					if (oCell!=null){ Row.removeCell(oCell);}
					}						
				}
			}
		
		  } 
		}catch(NullPointerException e) {
			e.printStackTrace();		
		}
		
		FileOutputStream fout = new FileOutputStream(FilePath);
		objWB.write(fout);
		fout.close();
		
	}
	
}
