package com.selenium.test;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HTML_Report {
	
	static Keywords common = new Keywords();
	static String strResultsPath = "C:\\WorkSpace\\Selenium_Hybrid_Framework\\Results";		
	
	static Calendar cal = Calendar.getInstance();
	static String strResultsFolder = "Run_" + cal.get(Calendar.DATE) + "_" + (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.YEAR) + "_" + cal.get(Calendar.HOUR_OF_DAY) + "_" + cal.get(Calendar.MINUTE) + "_" + cal.get(Calendar.SECOND);
	
	static String strHTMLResultsPath = strResultsPath + "\\" + strResultsFolder + "\\HTMLResults";
	
	static File f = new File(strHTMLResultsPath + "\\Report.html");
	
	static String strHeadColor = "#687C7D";
	static String strSettColor = "#C6D0D1";
	static String strContentBGColor = "#FFFFFF";
	
	
	public void GenerateReport(String strStartTime) throws Exception {
	
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		String dateStart = strStartTime;
		String dateStop = sdf.format(cal.getTime());
		Date d1 = null;
		Date d2 = null;
 
		d1 = sdf.parse(dateStart);
		d2 = sdf.parse(dateStop);
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		//long diffDays = diff / (24 * 60 * 60 * 1000);
		
		//System.out.print(diffDays + " days, ");
		System.out.print(diffHours + " hours, ");
		System.out.print(diffMinutes + " minutes, ");
		System.out.print(diffSeconds + " seconds.");
		
		
		boolean success = new File(strResultsPath + "\\" + strResultsFolder).mkdir();
		
		if (success) {
			System.out.println("New Folder Created. FolderName:" + strResultsPath + "\\" + strResultsFolder);
			boolean successHTML = new File(strHTMLResultsPath).mkdir();
			
			f.createNewFile();
			System.out.println("New File Created. File Name:" + strHTMLResultsPath + "\\" + f.getName() );
			
			char chr = 34;

			//======================COPY THE SCREENSHOTS FOLDER INTO RESULTS FOLDER========================//
			
			
			File ScreenshotsSourceFldr = new File(System.getProperty("user.dir") + "//screenshots");
			File ScreenshotsDestFldr = new File(System.getProperty("user.dir") + "//Results//" + strResultsFolder + "//screenshots");	
			
			if(ScreenshotsSourceFldr.exists()) {
				FileUtils.copyDirectory(ScreenshotsSourceFldr, ScreenshotsDestFldr);
				//======================DELETE SCREENSHOTS SOURCE FOLDER==========================//
				FileUtils.deleteDirectory(ScreenshotsSourceFldr);
			}
			
			//=====================COPY THE XLS FILES AS PER RUN MODE IN SUITE XLS TO RESULTS FOLDER==========================//
			String strSuitexlsFilePath = System.getProperty("user.dir") + "\\src\\com\\selenium\\xls\\Suite.xlsx";
			String strSHSuite = "Test Suite";
			
			FileInputStream oSuiteFile = new FileInputStream(strSuitexlsFilePath);
			XSSFWorkbook oSuiteWB = new XSSFWorkbook(oSuiteFile);
			XSSFSheet oSuiteSH = oSuiteWB.getSheet(strSHSuite);
			XSSFRow oSuiteRow = oSuiteSH.getRow(0);
			int SuiteLastRowNum = oSuiteSH.getLastRowNum();
			
			for(int SuiteRowIterate=1;SuiteRowIterate<SuiteLastRowNum+1;SuiteRowIterate++){
				String strSuiteRunMode = common.GetSheetRowVal(strSuitexlsFilePath, strSHSuite, SuiteRowIterate, 2);
				if(strSuiteRunMode.equals("Y")) {
					String strTestDataFileName = common.GetSheetRowVal(strSuitexlsFilePath, strSHSuite, SuiteRowIterate, 0);
					File oTestDataSrcFile = new File(System.getProperty("user.dir") + "\\src\\com\\selenium\\xls\\" + strTestDataFileName + ".xlsx");
					File oTestDataDestFile = new File(System.getProperty("user.dir") + "\\Results\\" + strResultsFolder + "\\xls");
					FileUtils.copyFileToDirectory(new File(strSuitexlsFilePath), oTestDataDestFile);             //==========copy Suite file to results directory========//
					if(oTestDataSrcFile.exists()) FileUtils.copyFileToDirectory(oTestDataSrcFile, oTestDataDestFile);
				}
			}
			
			
			
			
			String strProjectName ="selenium ";
			
			
			WriteLine("<html>");
			WriteLine("<head>");
			WriteLine("<meta http-equiv=" + "Content-Language" + "content=" + "en-us>");
			WriteLine("<meta http-equiv=" + "Content-Type" + "content=" + "text/html; charset=windows-1252" + ">");
			WriteLine("<title> Test Case Automation Execution Results</title>");
			WriteLine("<script>");
			WriteLine("top.window.moveTo(0, 0);");
			WriteLine("window.resizeTo(screen.availwidth, screen.availheight);");
			WriteLine("</script>");		
			WriteLine("</head>");
			
			WriteLine("<body bgcolor = #FFFFFF>");
			WriteLine("<blockquote>");			
			WriteLine("<p align = center><table border=1 bordercolor=" + "#000000 id=table1 width=900 height=31 cellspacing=0 bordercolorlight=" + "#FFFFFF>");
			WriteLine("<tr>");
			WriteLine("<td COLSPAN = 6 bgcolor ="+ strHeadColor + ">");
			WriteLine("<p align=center><font color=#FFFFFF size=4 face= "+ chr +"Copperplate Gothic Bold"+ chr + ">&nbsp;" + strProjectName + "AUTOMATION EXECUTION RESULTS" + "</font><font face= " + chr+"Copperplate Gothic Bold"+chr + "></font> </p>");
			WriteLine("</td>");
			WriteLine("</tr>");
					
			WriteLine("<tr>");
			WriteLine("<td COLSPAN = 6 bgcolor ="+ strHeadColor + ">");
			WriteLine("<p align=center><b><font color=#FFFFFF size=2 face= Verdana>"+ "&nbsp;"+ "DATE: " + cal.getTime());
			WriteLine("</td>");					
			WriteLine("</tr>");
					
			WriteLine("<table border=1 bordercolor=" + "#000000 id=table1 width=900 height=31 cellspacing=0 bordercolorlight=" + "#FFFFFF>");				
			WriteLine("<tr bgcolor = "+ strSettColor + ">");
			WriteLine("<td colspan =2>");
				WriteLine("<p align=center><b><font color=" + strHeadColor + " size=2 face= Verdana>"+ "&nbsp;"+ "Test Execution Time:" + diffHours + " Hour(s) " + diffMinutes + " Minute(s) " + diffSeconds + " Seconds"); 
			WriteLine("</td>");					  
			/*			
			WriteLine("<td colspan =2>");
				WriteLine("<p align=right><b><font color=" + strHeadColor + " size=2 face= Verdana>"+ "&nbsp;"+ "IterationMode: " +  strIterationMode )
			WriteLine("</td>"); 
			WriteLine("</tr>"); 
			*/
			
			 WriteLine("<table>");				
				
				WriteLine("<p align = center><table border=1 bordercolor=" + "#000000 id=table1 width=900 height=31 cellspacing=0 bordercolorlight=" + "#FFFFFF>");
					WriteLine("<tr bgcolor=" + strHeadColor + ">");
						WriteLine("<td width=" + "400");
							WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">" + "TCID</b>");
						WriteLine("</td>");
						
						WriteLine("<td width=" + "400");
							WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">" + "TSID</b>");
						WriteLine("</td>");
						
						WriteLine("<td width=" + "400");
							WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">" + "Description</b>");
						WriteLine("</td>");
						
						WriteLine("<td width=" + "400");
							WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">" + "Keyword</b>");
						WriteLine("</td>");
						/*
						WriteLine("<td width=" + "400");
							WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">" + "Status</b>");
						WriteLine("</td>");
						*/
					
				//End of Header
					
				
				
				//TempResultsFilePath = "C:\\Workspace\\ISACA_Hybrid_Framework\\src\\com\\isaca\\xls\\Sample_Report.xls";
				String strTempResultsFilePath = "C:\\WorkSpace\\Selenium_Hybrid_Framework\\src\\com\\selenium\\xls\\DEMO.xlsx";
				
				
				
				String strSHTempResults = "Test Steps";
				String strResultsRowHeader = "TCID";
				
				FileInputStream objFile = new FileInputStream(strTempResultsFilePath);
				XSSFWorkbook oWB = new XSSFWorkbook(objFile);
				XSSFSheet oSH = oWB.getSheet(strSHTempResults);
				XSSFRow oRow = oSH.getRow(0);
				int LastRowNum = oSH.getLastRowNum();			
				
				int TotalNoOfSteps = 0;
				int intPassCounter = 0;
				int intFailCounter = 0;
				
				int ResultIterator = 0;
				int ResultColIterate = 0;
				String strIteration = "";
				
				for(ResultColIterate=7;ResultColIterate>0;ResultColIterate++) {
					strIteration = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, 0, ResultColIterate);
					if (strIteration=="") {
						WriteLine("</tr>");
						break;
					}
					else {
						
						//==================ADD THE NEW ITERATION STATUS COLUMN====================//
						ResultIterator = ResultIterator + 1;
						WriteLine("<td width=" + "400");
						WriteLine("<p align=" + "center><b><font color = white face=" + "Arial Narrow " + "size=" + "2" + ">STATUS(Iteration:" + ResultIterator +")</b>");
						WriteLine("</td>");
		
					}			
						
				}
				
				
				
				String strStatus = "";
				
				
				for(int RowIterate = 1; RowIterate<=LastRowNum + 1;RowIterate++){
					
					String strTCID = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, 0);
					String strTSID = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, 1);
					String strDescription = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, 2);
					String strKeyword = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, 3);
					
					int StatusIterate = 0;
					boolean StatusPresent = false;
					
					for(StatusIterate = 1; StatusIterate<=ResultIterator;StatusIterate++) {
						strStatus = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, StatusIterate + 6);
						if (!strStatus.equals("")) { 
							StatusPresent = true;
							TotalNoOfSteps = TotalNoOfSteps + 1;
						}
					}
					
					
					
					
					if (StatusPresent==true) {						
						
						
						if (strTCID.equals("")) {
							 WriteLine("<tr bgcolor =" + strContentBGColor + ">");
								WriteLine("<td COLSPAN = 6>");
									WriteLine("<p align=center><b><font size=2 face= Verdana>"+ "&nbsp;"+ strDescription + ":&nbsp;&nbsp;" +  strStatus  + "&nbsp");
								WriteLine("</td>");
							WriteLine("</tr>");
							RowIterate = RowIterate+1;
						} else {
							WriteLine("<tr bgcolor =" + strContentBGColor + ">");
							WriteLine("<td width=" + "400>");
								WriteLine("<p align=" + "center><font face=" + "Verdana " + "size=" + "2" + ">"  +  strTCID);
							WriteLine("</td>");
							
							WriteLine("<td width=" + "400>");
							
							WriteLine("<p align=" + "center><font face=" + "Verdana " + "size=" + "2" + ">"  +  strTSID);
							WriteLine("</td>");
							
							 WriteLine("<td width=" + "400>");
									WriteLine("<p align=" + "center><font face=" + "Verdana " + "size=" + "2" + ">"  +  strDescription);
							 WriteLine("</td>");
							 
							WriteLine("<td width=" + "400>");
							if (strKeyword.equals("Pass")) {
								WriteLine("<p align=" + "center" + ">" + "<b><font face=" + "Verdana " + "size=" + "2" + " color=" + "#008000" + ">" + strKeyword + "</font></b>");
								//intPassCounter=intPassCounter + 1	
								//intVerificationNo=intVerificationNo + 1
							} else if (strKeyword.equals("Fail")) {							
								WriteLine("<p align=" + "center" + ">" + "<b><font face=" + "Verdana " + "size=" + "2" + " color=" + "#FF0000" + ">" + strKeyword + "</font></b>");
								//intFailCounter=intFailCounter + 1
							    //intVerificationNo=intVerificationNo + 1
							} else {
								
								WriteLine("<p align=" + "center" + ">" + "<b><font face=" + "Verdana " + "size=" + "2" + " color=" + "#8A4117" + ">" + strKeyword + "</font></b>");
							}						
							
						WriteLine("</td>");
						
						
						//==================ITERATE THE RESULT COLUMNS AND ADD THE STATUS===================//
						
						
						for(int i=1;i<=ResultIterator;i++){
							
							strStatus = common.GetSheetRowVal(strTempResultsFilePath, strSHTempResults, RowIterate, i+6);
							if (strStatus.toLowerCase().equals("pass")) intPassCounter = intPassCounter + 1;
							if (strStatus.contains("FAIL")) intFailCounter = intFailCounter + 1;
							WriteLine("<td width=" + "400>");
							WriteLine("<p align=" + "center><font face=" + "Verdana " + "size=" + "2" + ">"  +  strStatus);
							WriteLine("</td>");								
						}
						
						WriteLine("</tr>");
						
						/*
						WriteLine("<td width=" + "400>");
						WriteLine("<p align=" + "center><font face=" + "Verdana " + "size=" + "2" + ">"  +  strStatus);
						WriteLine("</td>");
						WriteLine("</tr>");				
						*/
						
						}	
						
					}
					
				}
				
				//============COMPLETE THE FOOTER SECTION========================//
				WriteLine("</table>");				
				
				//TotalNoOfSteps = TotalNoOfSteps * ResultIterator;
				
				WriteLine("<table border=1 bordercolor=" + "#000000 id=table1 width=900 height=31 cellspacing=0 bordercolorlight=" + "#FFFFFF>");
				WriteLine("<tr bgcolor =" + strSettColor + ">");
					WriteLine("<td colspan =1>");
						WriteLine("<p align=justify><b><font color=" + strHeadColor + "  size=2 face= Verdana>"+ "&nbsp;"+ "No of Test Steps Executed :&nbsp;" + TotalNoOfSteps+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					WriteLine("</td>");
					
					WriteLine("<td colspan =1>");	
						WriteLine("<p align=justify><b><font color=" + strHeadColor + "  size=2 face= Verdana>"+ "&nbsp;"+ "Passed :&nbsp;&nbsp;" +  intPassCounter+"&nbsp;");
					WriteLine("</td>");
					
					WriteLine("<td colspan =1>");	
						WriteLine("<p align=justify><b><font color=" + strHeadColor + "  size=2 face= Verdana>"+ ""+ "Failed :&nbsp;&nbsp;" +  intFailCounter);
					WriteLine("</td>");	
				WriteLine("</tr>");	
				WriteLine("</table>");				
				WriteLine("</blockquote>");			
				WriteLine("</body>");
				WriteLine("</html>");
			}

		String fileName = strHTMLResultsPath + "\\Report.html";
		String[] commands = {"cmd", "/c", "start", "\"DummyTitle\"",fileName};
		Runtime.getRuntime().exec(commands);
	}
	
	
	public static void WriteLine(String text) throws IOException {
		Writer w;
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bw = new BufferedWriter(fw);	
		bw.write(text);
		bw.newLine();
		bw.close();
	}

}
