package Test.Crack;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class PchScenarios {

	public String url = "https://iwe.qa.pch.com/iwe/#user/list";
	public By username = By.cssSelector("#j_username");
	public By password = By.cssSelector("#j_password");
	public By loginButton = By.cssSelector("#submit");
	public By devicesButton = By.cssSelector("a #button-1018-btnInnerEl");
	public By firstListItem = By.cssSelector("[role='row']:first-child td:nth-child(3)");
	public By gwyGroupDropdown = By.name("giveawayGroupData.id");
	public By firstSuggestion = By.cssSelector("ul[class='x-list-plain']>li:first-child");

	public WebDriver driver = null;

	@BeforeTest
	public void initialize() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\karunachalam\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void selectItemsFromDropdown() throws Exception {
		String expectedValue = "Autoitaa";
		driver.get(url);
		getElement(username).sendKeys("karunachalam");
		getElement(password).sendKeys("Winter2017");
		clickElement(loginButton);
		waitForPageToLoad();
		clickElementJS(devicesButton);
		clickElement(firstListItem);
		getElement(gwyGroupDropdown).clear();
		getElement(gwyGroupDropdown).sendKeys(expectedValue);
		clickElement(firstSuggestion);
		String actualValue = getElement(gwyGroupDropdown).getAttribute("value").trim();
		System.out.println(actualValue);
		Assert.assertEquals(expectedValue, actualValue);
	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	public WebElement getElement(By locator) throws Exception {
		wait(3);
		waits(30, locator);
		return driver.findElement(locator);
	}

	public void clickElement(By locator) throws Exception {
		waits(30, locator);
		driver.findElement(locator).click();
	}

	public void waits(int seconds, By locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void clickElementJS(By locator) throws Exception {
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(locator));
	}

	public void wait(int seconds) throws Exception {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public void waitForPageToLoad() throws Exception {
		((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	}

}
