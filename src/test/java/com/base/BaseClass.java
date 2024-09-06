package com.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	
	public static WebDriver driver;
	static Select select;
	
	// 1 To enter the URL
	public static void enterApplnUrl(String url) {
		driver.get(url);
	}

	// 2 To maximize the window
	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	// 3 To enter the text using sendkeys
	public void elementSendKeys(WebElement element, String data) {
		elementVisibility(element);

		if (elementIsDisplayed(element) && elementIsEnabled(element)) {
			element.sendKeys(data);
		}
	}

	// 4 to perform click
	public void elementClick(WebElement element) {
		elementVisibility(element);
		if (elementIsDisplayed(element) && elementIsEnabled(element)) {
			element.click();
		}
	}

	// 5 to accept alert
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	// 6 to dismiss alert
	public void cancelAlert() {
		driver.switchTo().alert().dismiss();
	}

	// 7 To get text
	public String elementGetText(WebElement element) {
		elementVisibility(element);
		String text = element.getText();
		return text;
	}

	// 8 To get the inserted value from textbox

	// 99%--->value fixed
	public String elementGetAttributeValue(WebElement element) {
		elementVisibility(element);
		String attribute = null;
		if (elementIsDisplayed(element) && elementIsEnabled(element)) {
			attribute = element.getAttribute("value");
		}
		return attribute;

	}

	// 1%--->value is NOT fixed
	public String elementGetAttributeValue(WebElement element, String attributeName) {
		elementVisibility(element);
		String attribute = null;
		if (elementIsDisplayed(element) && elementIsEnabled(element)) {
			attribute = element.getAttribute(attributeName);
		}
		return attribute;
	}

	// 9 Close all windows
	public static void quitWindow() {
		driver.quit();
	}

	// 10 Close current window
	public static void closeWindow() {
		driver.close();
	}

	// 11 To get title
	public String getApplnTitle() {
		String title = driver.getTitle();
		return title;
	}

	// 12To get current URL
	public String getAppnUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}

	// 13 To select dropdown by text
	public void selectOptionByText(WebElement element, String text) {
		elementVisibility(element);
		select = new Select(element);
		select.selectByVisibleText(text);
	}

	// 14 To select dropdown by value
	public void selectOptionByValue(WebElement element, String text) {
		elementVisibility(element);
		select = new Select(element);
		select.selectByValue(text);
	}

	// 15 To select dropdown by index
	public void selectOptionByIndex(WebElement element, int index) {
		elementVisibility(element);
		select = new Select(element);
		select.selectByIndex(index);
	}

	// 16 Insert value in textbox using JS
	public void elementSendKeysJs(WebElement element, String data) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].setAttribute('value','" + data + "')", element);
	}

	// 17 Click button using JS
	public void elementClickJs(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", element);
	}

	// 18 To launch the browser
	public static void browserLaunch(String browserType) {
		switch (browserType) {
		case "CHROME":
			driver = new ChromeDriver();
			break;
		case "IE":
			driver = new InternetExplorerDriver();
			break;
		case "FIREFOX":
			driver = new FirefoxDriver();
			break;
		case "EDGE":
			driver = new EdgeDriver();
			break;
		default:
			break;
		}
	}

	// 19 To switch to child windows
	public void switchToChildWindow() {
		String pWindow = driver.getWindowHandle();
		Set<String> allWindowsId = driver.getWindowHandles();

		for (String eachWindowId : allWindowsId) {
			if (!pWindow.equals(eachWindowId)) {
				driver.switchTo().window(eachWindowId);
			}
		}
	}

	// 20 To switch to frame by index
	public void switchFrameByIndex(int index) {
		driver.switchTo().frame(index);
	}

	// 21 To switch to frame using id
	public void switchFrameById(WebElement id) {
		driver.switchTo().frame(id);
	}

	// 22 To find locator by ID
	public WebElement findLocatorById(String attributeValue) {
		WebElement findElement = driver.findElement(By.id(attributeValue));
		return findElement;
	}

	// 23To find locator by name
	public WebElement findLocatorByName(String attributeValue) {
		WebElement findElement = driver.findElement(By.name(attributeValue));
		return findElement;
	}

	// 24 To find locator by className
	public WebElement findLocatorByClassName(String attributeValue) {
		WebElement findElement = driver.findElement(By.className(attributeValue));
		return findElement;
	}

	// 25 To find locator by Attribute value
	public WebElement findLocatorByXpath(String attributeValue) {
		WebElement findElement = driver.findElement(By.xpath(attributeValue));
		return findElement;
	}

	// 26 Get all options from dropdown as text
	public List<String> getAllOptionsText(WebElement element) {
		List<String> lstText = new ArrayList<>();
		select = new Select(element);
		List<WebElement> options = select.getOptions();
		for (WebElement webElement : options) {
			String text = webElement.getText();
			lstText.add(text);
		}
		return lstText;
	}

	// 27 Get all options from dropdown using attribue value
	public List<String> getAllOptionsValue(WebElement element) {
		List<String> lstText = new ArrayList<>();
		select = new Select(element);
		List<WebElement> options = select.getOptions();

		for (int i = 0; i < options.size(); i++) {
			WebElement attribute = options.get(i);
			String text = attribute.getAttribute("value");
			lstText.add(text);
		}
		return lstText;
	}

	// 28 get the first selected option in dropdown
	public String firstSelectedOption(WebElement element) {
		select = new Select(element);
		WebElement firstSelectedOption = select.getFirstSelectedOption();
		String text = firstSelectedOption.getText();
		return text;
	}

	// 29 Verify if dropdown in multi selected
	public boolean isMultiSelected(WebElement element) {
		Select s = new Select(element);
		boolean multiple = s.isMultiple();
		return multiple;
	}

	// 30 Implicit wait using variable
	public void implicitWait(int secs) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(secs));
	}

	// implicit wait using default 60 seconds
	public static void implicitWait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	// 31 Explicit Wait
	public void elementVisibility(WebElement element) {
		WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driverWait.until(ExpectedConditions.visibilityOf(element));

	}

	// 32 Fluent Wait

	// 33 To check if the element is displayed
	public boolean elementIsDisplayed(WebElement element) {
		elementVisibility(element);
		boolean displayed = element.isDisplayed();
		return displayed;
	}

	// 34 To check if the element is enabled
	public boolean elementIsEnabled(WebElement element) {
		elementVisibility(element);
		boolean enabled = element.isEnabled();
		return enabled;
	}

	// 35 To check if the element is selected
	public boolean elementIsSelected(WebElement element) {
		elementVisibility(element);
		boolean selected = element.isSelected();
		return selected;
	}

	// 36 Deselect by Index
	public void deselectByIndex(WebElement element, int index) {
		Select s = new Select(element);
		s.deselectByIndex(index);
	}

	// 37 Deselect by Attribute
	public void deselectByAttribute(WebElement element, String attribute) {
		Select s = new Select(element);
		s.deselectByValue(attribute);
	}

	// 38 Deselect by Text
	public void deselectByVisibleText(WebElement element, String attribute) {
		Select s = new Select(element);
		s.deselectByVisibleText(attribute);
	}

	// 39 Deselect All
	public void deselectAll(WebElement element) {
		Select s = new Select(element);
		s.deselectAll();
	}

	// 40 To get parent Window
	public String parentWindowId() {
		String windowHandle = driver.getWindowHandle();
		return windowHandle;
	}

	// 41 to get all windows
	public Set<String> getAllWindowsId() {
		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	}

	// 42 Clear Textbox
	public void elementClear(WebElement element) {
		elementVisibility(element);
		if (elementIsDisplayed(element) && elementIsEnabled(element)) {
			element.clear();
		}
	}

	// 43Take Screenshot
	public void screenshot(String sName) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File s = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(s, new File(getProjectPath() + "\\images\\" + sName + ".png"));
	}

	// 44 Take screenshot of an element
	public void screenshot(WebElement element, String sName) throws IOException {
		File s = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(s, new File(getProjectPath() + "\\images\\" + sName + ".png"));
	}

	// 45 movetoElement
	public void moveToElement(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	// 46 Drag & drop
	public void dragAndDrop(WebElement source, WebElement destination) {
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, destination).perform();
	}

	// 47 Right Click
	public void rightClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}

	// 48 Double Click
	public void doubleClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}

	// 49 Insert value and press enter
	public void pressEnter() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	// 50 refresh page
	public void pageRefresh() {
		driver.navigate().refresh();
	}

	// 51 To get project directory
	public String getProjectPath() {
		String property = System.getProperty("user.dir");
		return property;
	}

	// 52 To press enter
	public void elementSendKeysEnter(WebElement element, String data) {
		elementVisibility(element);

		if (elementIsDisplayed(element) && (elementIsEnabled(element))) {
			element.sendKeys(data, Keys.ENTER);
		}
	}

	// 53 To enter data in prompt alert
	public void promptAlert(String data) {
		driver.switchTo().alert().sendKeys(data);
	}

	// 54 To fetch a data freom excell
	public String getCellData(String sheetname, int rownum, int cellnum) throws IOException {
		String res = null;
		File file = new File(getProjectPath() + "\\Excel Folder\\grocery-test-data.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetname);
		Row row = sheet.getRow(rownum);
		Cell cell = row.getCell(cellnum);
		CellType type = cell.getCellType();

		switch (type) {

		case STRING:
			res = cell.getStringCellValue();
			break;

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date dateCellValue = cell.getDateCellValue();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
				res = dateFormat.format(dateCellValue);
			} else {
				double numericCellValue = cell.getNumericCellValue();
				long round = Math.round(numericCellValue);
				if (numericCellValue == round) {
					res = String.valueOf(round);
				} else {
					res = String.valueOf(numericCellValue);
				}
			}
			break;

		default:
			break;

		}
		return res;

	}
	
	// 55 To update a cell data
	public void updateCellData(String sheetName, int rowNum, int cellNum, String oldData, String newData) throws IOException {
		File file = new File(getProjectPath() + "\\Excel Folder\\");
		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		String value = cell.getStringCellValue();
		if (value.equals(oldData)) {
			cell.setCellValue(newData);
		}
		FileOutputStream fileOuputStream = new FileOutputStream(file);
		workbook.write(fileOuputStream);
	}
	
	// 56 to write a value in cell
	public void setCellData(String sheetName, int rowNum, int cellNum, String data) throws IOException {
		File file = new File(getProjectPath() + "\\Excel Folder\\");
		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.createCell(cellNum);
		cell.setCellValue(data);
		FileOutputStream fileOuputStream = new FileOutputStream(file);
		workbook.write(fileOuputStream);
	}

	//57 Click Iteration
	public void iterateClick(WebElement element) {
		elementVisibility(element);
		Select s = new Select(element);
		List<WebElement> options = s.getOptions();
		for (WebElement webElement : options) {
			webElement.click();
			
		}
	}
	
	//58 Default content
	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}
	
	//59 Project Path
	public String getPropertyFileValue(String key) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(getProjectPath() + "\\config\\config.properties"));
		Object object = properties.get(key);
		String value = (String) object;
		return value;
	}
	
}
