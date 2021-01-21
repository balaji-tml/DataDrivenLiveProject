package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.w2a.base.TestBase;

public class TestUtil extends TestBase {

	public static String screenshotPath, screenshotName;

	public static void captureScreenshot() {
		Date d = new Date();
		// screenshotName = TestUtil.screenshotName;
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		System.out.println("screenshotName: " + screenshotName); // Remove after dry-run
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(userDir + "\\target\\surefire-reports\\html\\" + screenshotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String,String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}
	
	/*
	@DataProvider(name = "dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rows = excel.getRows(sheetName);
		int cols = excel.getCols(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}

		return data;
	}
	*/
	
	public static boolean isTestRunnable(String testname,ExcelReader excel)
	{
		String sheetName = "test_suite";
		String testcase,runmode;
		int rows = excel.getRowCount(sheetName);
		for(int rowNum =2;rowNum<=rows;rowNum++)
		{
			testcase = excel.getCellData(sheetName, "TC_ID", rowNum);
			System.out.println("testcase: "+testcase);
			if(testcase.equalsIgnoreCase(testname))
			{
				runmode = excel.getCellData(sheetName, "Runmode", rowNum);
				System.out.println("runmode: "+runmode);
				if(runmode.equalsIgnoreCase("Y"))
				
					return true;
				else
					
					return false;
			}
		}
		return false;
	}
	
	
}// End of TestUtil Class
