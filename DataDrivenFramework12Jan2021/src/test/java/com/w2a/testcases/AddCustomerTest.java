package com.w2a.testcases;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sun.rowset.internal.Row;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) {
	
		String firstname = data.get("firstname");
		String lastname = data.get("lastname");
		String postcode = data.get("postcode");
		String alerttext = data.get("alerttext");
		String runmode = data.get("runmode");
		
		if (!(runmode.equals("Y"))) {
			throw new SkipException("Skipping the testcase as the Runmode is No");//
		}
		log.debug("Inside Add Customer Test!!!");

		String actualAlertText;

		click("addCustBtn_CSS");
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		type("firstname_CSS", firstname);
		type("lastname_CSS", lastname);
		type("postcode_CSS", postcode);		 
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("addBtn_CSS"))));
		click("addBtn_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		actualAlertText = alert.getText();
		System.out.println("actualAlertText: " + actualAlertText); // Remove after dry-run		
		Assert.assertTrue(actualAlertText.contains(alerttext));
		alert.accept();
		log.debug("Add Customer Test executed successfully!!!");
	}

	
}// End of TestUtil class
