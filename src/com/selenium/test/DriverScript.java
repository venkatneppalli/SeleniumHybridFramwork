package com.selenium.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.selenium.xls.read.Xls_Reader;

public class DriverScript {

	public static Logger APP_LOGS;
	//suite.xlsx
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public static String currentTestSuite;
	
	// current test suite
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID=2;
	public static Method method[];
	
	public static Method capturescreenShot_method;
	

	public static Keywords keywords;
	//public static ApplicationKeywords appkeywords;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;
	public static String data;
	public static String object;
	
	// properties
	public static Properties CONFIG;
	public static Properties OR;
	public static String FilePath;
	public static String SheetName;
	
	WebDriver driver = null;
	


	public DriverScript() throws Exception{
		keywords = new Keywords();
				
		method = keywords.getClass().getMethods();
		
		capturescreenShot_method =keywords.getClass().getMethod("captureScreenshot1",String.class,String.class);
		
		//FilePath = System.getProperty("user.dir")+"//src//com//selenium//xls//TestISACA.xlsx";
		//SheetName = "Test Steps";
		
		keywords.DeleteResultsColumn(FilePath, SheetName);
	}
	
	public static void main(String[] args) throws Exception,NullPointerException {
			
		
		
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//selenium//config//config.properties");
		CONFIG= new Properties();
		CONFIG.load(fs);
		
		fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//selenium//config//or.properties");
		OR= new Properties();
		OR.load(fs);
		
		
		//Runtime.getRuntime().exec("C:\\Workspace\\ISACA_Hybrid_Framework\\Results\\Run_20130423205620\\HTMLResults\\Report.html");
		
		//System.out.println(CONFIG.getProperty("testsiteURL"));
		//System.out.println(OR.getProperty("name"));
		
		//================================FORMAT TEST DATA SHEET BY DELETING THE RESULTS COLUMN======================//
		
		
		DriverScript test = new DriverScript();
		
		String strCurrentTime = sdf.format(cal.getTime());
		test.start();
		
		HTML_Report report = new HTML_Report();
		report.GenerateReport(strCurrentTime);
	}
	
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException,IOException{
		// initialize the app logs
		BasicConfigurator.configure();
		APP_LOGS = Logger.getLogger("ISACALogger");
		//APP_LOGS = Logger.getLogger(DriverScript.class);
		//APP_LOGS = Logger.getLogger(DriverScript.class.getName());
		APP_LOGS.debug("Hello");
		APP_LOGS.debug("Properties loaded. Starting testing");
		// 1) check the runmode of test Suite
		// 2) Runmode of the test case in test suite
	    // 3) Execute keywords of the test case serially
		// 4) Execute Keywords as many times as
		// number of data sets - set to Y
		APP_LOGS.debug("Intialize Suite xlsx");
		suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//selenium//xls//Suite.xlsx");
		
		
		for(currentSuiteID=2;currentSuiteID<=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);currentSuiteID++){
		
			APP_LOGS.debug(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID)+" -- "+  suiteXLS.getCellData("Test Suite", "Runmode", currentSuiteID));
			// test suite name = test suite xls file having tes cases
			currentTestSuite=suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID);
			if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID).equals(Constants.RUNMODE_YES)){
				// execute the test cases in the suite
				APP_LOGS.debug("******Executing the Suite******"+suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID));
				currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+"//src//com//selenium//xls//"+currentTestSuite+".xlsx");
				// iterate through all the test cases in the suite
				for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Cases");currentTestCaseID++){				
					APP_LOGS.debug(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID)+" -- "+currentTestSuiteXLS.getCellData("Test Cases", "Runmode", currentTestCaseID));
					currentTestCaseName=currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
									
					if(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(Constants.RUNMODE_YES)){
						APP_LOGS.debug("Executing the test case -> "+currentTestCaseName);
					 if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
					  	// RUN as many times as number of test data sets with runmode Y
					  for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(currentTestCaseName);currentTestDataSetID++)	
					  {
						resultSet = new ArrayList<String>();
						
						// checking the runmode for the current data set
					   if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(Constants.RUNMODE_YES)){
						   APP_LOGS.debug("Iteration number "+(currentTestDataSetID-1));
					    // iterating through all keywords	
						   executeKeywords(); // multiple sets of data
						   createXLSReport();
					   }
					   //createXLSReport();
					  }
					 }else{
						// iterating through all keywords	
						 resultSet= new ArrayList<String>();
						 executeKeywords();// no data with the test
						 createXLSReport();
					 	}
					}
				}
			}
		}	
	}
	
	
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NullPointerException, IOException {
		// iterating through all keywords	
		for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);currentTestStepID++){
			// checking TCID
		  if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, currentTestStepID))){
						
			data=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA,currentTestStepID);
			if(data.startsWith(Constants.DATA_START_COL)){
				// read actual data value from the corresponding column				
				data=currentTestSuiteXLS.getCellData(currentTestCaseName, data.split(Constants.DATA_SPLIT)[1] ,currentTestDataSetID );
			}else if(data.startsWith(Constants.CONFIG)){
				//read actual data value from config.properties		
				data=CONFIG.getProperty(data.split(Constants.DATA_SPLIT)[1]);
			}else{
				//by default read actual data value from or.properties
				data=OR.getProperty(data);
			}
			object=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT,currentTestStepID  );
				currentKeyword=currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD, currentTestStepID);
				//APP_LOGS.debug(currentKeyword);          //Temporarily Stop displaying the keyword on console
				// code to execute the keywords as well
			    // reflection API
				
				boolean MethodPresent = false;
				
				for(int i=0;i<method.length;i++){
					
					if(method[i].getName().equals(currentKeyword)){
					MethodPresent = true;	
					keyword_execution_result=(String)method[i].invoke(keywords,object,data);
					APP_LOGS.debug(keyword_execution_result);
					if (keyword_execution_result.equals("FAIL")) APP_LOGS.debug(keyword_execution_result);
					resultSet.add(keyword_execution_result);
					
					// capture screenshot		
					
					keywords.CaptureScreenshot();	
					//keywords.captureScreenshot1(currentTestSuite+"_"+ currentTestCaseName+"_TS"+ currentTestStepID+"_"+(currentTestDataSetID-1), keyword_execution_result);
					
					}
					
				}
				if (MethodPresent==false) {
					//appkeywords = new ApplicationKeywords(keywords.driver);
					//method = appkeywords.getClass().getMethods();
					for(int i=0;i<method.length;i++){
						
						if(method[i].getName().equals(currentKeyword)){
						MethodPresent = true;
						
						//keyword_execution_result=(String)method[i].invoke(appkeywords,object,data);
						APP_LOGS.debug(keyword_execution_result);
						if (keyword_execution_result.equals("FAIL")) APP_LOGS.debug(keyword_execution_result);
						resultSet.add(keyword_execution_result);
						
						// capture screenshot		
						
						keywords.CaptureScreenshot();	
						//keywords.captureScreenshot1(currentTestSuite+"_"+ currentTestCaseName+"_TS"+ currentTestStepID+"_"+(currentTestDataSetID-1), keyword_execution_result);
						
						}
						
					}
				}
			}	
		}
	}
	
	public void createXLSReport(){
		
		String colName=Constants.RESULT +(currentTestDataSetID-1);		
		boolean isColExist=false;
		boolean isIterationColExist=false;
		
		for(int c=0;c<currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);c++){
			if(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET,c , 1).equals(colName)){
				isColExist=true;
				break;
			}
		}
		
		for(int c=0;c<currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET);c++){
			if(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET,c , 1).equals("Iteration")){
				isIterationColExist=true;
				break;
			}
		}
		
		
		if(!isIterationColExist)
			//currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, "Iteration");
		
		if(!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
		int index=0;
		for(int i=2;i<=currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET);i++){
			
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, i))){
				if(resultSet.size()==0)
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);
				else	
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
					//currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, "Iteration", i, String.valueOf(currentTestDataSetID-1));
				index++;
			}
			
		}
		
		if(resultSet.size()==0){
			// skip
			currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
			return;
		}else{
			for(int i=0;i<resultSet.size();i++){
				if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, resultSet.get(i));
					return;
				}
			}
		}
		currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);
	//	if(!currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode",currentTestDataSetID).equals("Y")){}
		
	}
	
	
	
	
}
