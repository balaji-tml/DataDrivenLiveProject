package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel;
	public static WebDriverWait wait;
	protected static String userDir;
	public static ExtentReports rep= ExtentManager.getInstance();
	public static ExtentTest test;
	public static WebElement dropdown;
	static int implicitwait;
	int explicitwait;

	@BeforeSuite
	public void setUp() {
		userDir = System.getProperty("user.dir");
		String browser, testsiteurl;
		String excelpath;
		//int implicitwait,explicitwait;
		

		// Load Config Properties files
		try {
			fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			config.load(fis);
			log.debug("Config properties file loaded!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Load OR Properties files
		try {
			fis = new FileInputStream(userDir + "\\src\\test\\resources\\properties\\OR.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			OR.load(fis);
			log.debug("OR properties file loaded!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Declare and Instantiate Browser driver
		browser = config.getProperty("browser");
		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					userDir + "//src//test//resources//executables//chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					userDir + "//src//test//resources//executables//geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equals("edge")) {
			System.setProperty("webdriver.edge.driver",
					userDir + "//src//test//resources//executables//msedgedriver.exe");
			driver = new EdgeDriver();
		} else if (browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver",
					userDir + "//src//test//resources//executables//IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		//Instantiate ExcelReader
		excelpath = userDir + "//src//test//resources//excel//testdata.xlsx";
		//excelpath = "C:\\Users\\balaji\\git\\DataDrivenFramework\\DataDrivenFramework12Jan2021\\src\\test\\resources\\excel\\testdata.xlsx";
		excel = new ExcelReader(excelpath);
		
		implicitwait = Integer.parseInt(config.getProperty("implicit.wait"));
		explicitwait = Integer.parseInt(config.getProperty("explicit.wait"));
		//driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, explicitwait);
		// Launch test website
		testsiteurl = config.getProperty("testsiteurl");
		driver.get(testsiteurl);
		log.debug("Launch browser and navigated to: " + testsiteurl);
		driver.manage().window().maximize();
		// System.out.println("Test site page title: "+driver.getTitle()); //Remove after dry-run
		
	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
			log.debug("Test execution  completed!!!");
		}

	}
	
	public boolean isElementPresent(By by)
	{
		try
		{
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e)
		{
			return false;
		}
	}
	
	public void click(String locator)
	{
		if(locator.endsWith("_CSS"))
		{
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		else if(locator.endsWith("_ID"))
		{
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on: "+ locator);
	}
	
	public void type(String locator, String value)
	{
		if(locator.endsWith("_CSS"))
		{
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		else if(locator.endsWith("_ID"))
		{
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		
		test.log(LogStatus.INFO, "Typing in "+locator+" and entered value as: "+ value);
	}
	
	public void verifyEquals(String expected,String actual)
	{
		try
		{
			Assert.assertEquals(actual, expected);
		}catch(Throwable t)
		{
			TestUtil.captureScreenshot();
			//ReportNg
			Reporter.log("<br>"+"Verification failure:"+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200> </img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			//ExtentReports
			test.log(LogStatus.FAIL, "Verification failed with exception: "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		}
	}
	
	public static void select(String locator, String value)
	{
		/*
		 * if(locator.endsWith("_CSS")) { dropdown =
		 * driver.findElement(By.cssSelector(OR.getProperty(locator))); } else
		 * if(locator.endsWith("_ID")) { dropdown =
		 * driver.findElement(By.id(OR.getProperty(locator))); } else
		 * if(locator.endsWith("_XPATH")) { dropdown =
		 * driver.findElement(By.xpath(OR.getProperty(locator))); }
		 */			
		
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		Select select = new Select(dropdown);		
		select.selectByVisibleText(value);		
		test.log(LogStatus.INFO, "Selecting from dropdown: "+locator+" value as: "+ value);
	}
}//End of TestBase Class
