package com.w2a.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void bankManagerLoginTest()
	{
		log.debug("Inside BankManagerLoginTest!!!");		
		click("bmlBtn_CSS");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("addCustBtn_CSS"))));
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))));		
		log.debug("BankManagerLoginTest is successfully executed!!!");
	}

}
