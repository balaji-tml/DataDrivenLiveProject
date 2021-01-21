package com.w2a.testcases;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data) {
	
		 String customer = data.get("customer"); 
		 String currency = data.get("currency"); 
		 String alerttext = data.get("alerttext");
		 String runmode = data.get("runmode");
		 //System.out.println("customer: "+customer+", currency: " +currency+", alerttext: "+alerttext+", runmode"+runmode); //Remove after dry-run
		 
		 if (!(runmode.equals("Y"))) {
				throw new SkipException("Skipping the testcase as the Runmode is No");//
			}
		
		log.debug("Inside Open Account Test!!!");
		String actualAlertText;		 
		 
		//driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		click("openaccount_CSS");
		//driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("customer_ID"))));
		select("customer_ID", customer);
		select("currency_ID", currency);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("process_CSS"))));
		click("process_CSS");
		//driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		actualAlertText = alert.getText();
		System.out.println("actualAlertText: " + actualAlertText); // Remove after dry-run		
		Assert.assertTrue(actualAlertText.contains(alerttext));
		alert.accept();
		log.debug("Open Account Test executed successfully!!!");
	}

	
}// End of OpenAccountTest class
