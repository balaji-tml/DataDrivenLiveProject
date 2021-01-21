package com.w2a.rough;

import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class TestExcelReader extends TestBase{

	@Test
	public void testExcelReader()
	{
		int rows,cols;
		String sheetName, celldate;
		/*
		sheetName = "test_suite";		
		int rows = excel.getRows(sheetName);
		int cols = excel.getCols(sheetName);
		for(int i=1;i<=rows;i++) {
			for(int j=0;j<cols;j++) {
				String celldate = excel.getCellData(sheetName, j, i);
				System.out.println("Cell data at: row= "+i+" and col= "+j+" is "+celldate);
			}
		}
		*/
		sheetName = "AddCustomerTest";
		rows = excel.getRowCount(sheetName);
		cols = excel.getColCount(sheetName);
		for(int i=1;i<=rows;i++) {
			for(int j=0;j<cols;j++) {
				celldate = excel.getCellData(sheetName, j, i);
				System.out.println("Cell data at: row= "+i+" and col= "+j+" is "+celldate);
			}
		}
		
		//String TCID = excel.getCellData("test_suite", 0, 0)
	}
}
