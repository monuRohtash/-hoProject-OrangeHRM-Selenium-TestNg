package AutomationDemoSite;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomationSite {

	RemoteWebDriver driver;


	//	WebDriver driver;

	@BeforeMethod
	public void Setup() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		//		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://demo.automationtesting.in/Register.html");



		WebElement element = driver.findElement(By.xpath("//h1[normalize-space()='Automation Demo Site']"));

		String Exp = "Automation Demo Site";
		String Act = element.getText();
		System.out.println(Act);

		// Verify the text
		Assert.assertEquals(Act, Exp);

		//Optional 

		// Verify the text
		//		if (Act.equals(Exp)) {
		//		    System.out.println("Text verification passed!");
		//		} else {
		//		    System.out.println("Text verification failed!");
		//		}



	}






	@Test
	public void Test1() throws InterruptedException {

		try {

			SoftAssert SoftAssert = new SoftAssert();

			WebElement FirstN = driver.findElement(By.xpath("//input[@placeholder='First Name']"));
			FirstN.sendKeys("Monu");

			WebElement LastN = driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
			LastN.sendKeys("Raghav");

			WebElement Adress = driver.findElement(By.tagName("textarea"));
			Adress.sendKeys("New krishna Colony Palwal Hariyana 121102");

			WebElement Phone = driver.findElement(By.xpath("//input[@type='tel']"));
			Phone.sendKeys("8053208786");

			WebElement Email = driver.findElement(By.xpath("//input[@type='email']"));
			Email.sendKeys("monu@gmail.com");

			WebElement RadioButtonList = driver.findElement(By.xpath("//label[normalize-space()='Male']"));
			RadioButtonList.click();
			System.out.println("List: " + RadioButtonList.getText() );

			// Verify if it is selected

			if(RadioButtonList.isDisplayed()) {
				System.out.println("Radio button is selected!");
			}
			else {
				System.out.println("Radio button is not selected!");
			}


			// Select hobbies (checkboxes)

			driver.findElement(By.id("checkbox1")).click(); // Cricket
			driver.findElement(By.id("checkbox2")).click(); // Hockey
			driver.findElement(By.id("checkbox3")).click(); // Hockey

			// Select Language
			WebElement languageDropdown = driver.findElement(By.id("msdd"));
			languageDropdown.click();
			WebElement languageOption = driver.findElement(By.xpath("//a[contains(text(),'English')]"));
			languageOption.click();






			// Select Skills from dropdown
			Select skillsValue = new Select(driver.findElement(By.id("Skills")));
			skillsValue.selectByValue("APIs");

			// Select Date of Birth (Year, Month, Day dropdowns)
			driver.findElement(By.id("yearbox")).sendKeys("1996");
			driver.findElement(By.xpath("//select[@placeholder='Month']")).sendKeys("November");
			driver.findElement(By.id("daybox")).sendKeys("11");


			//Select Password and C-Password
			WebElement passwordField = driver.findElement(By.id("firstpassword"));
			passwordField.sendKeys("Raghav@123");
			WebElement confirmPasswordField = driver.findElement(By.id("secondpassword"));
			confirmPasswordField.sendKeys("Raghav@123");


			// Retrieve values from Password and C-Password fields
			String password = passwordField.getAttribute("value");
			String confirmPassword = confirmPasswordField.getAttribute("value");

			SoftAssert.assertEquals(password, confirmPassword, "Passwords do not match!");	

			Thread.sleep(2000);

			// Locate the file upload input field                    // Provide the file path to the input field                                 
			WebElement uploadFile = driver.findElement(By.cssSelector("#imagesrc"));
			uploadFile.sendKeys("/home/raghav/Documents/Project-OrangeHRM-Selenium-TestNg/image/11.png");

			if(uploadFile.isSelected()) {
				System.out.println("Upload file is successfull");
			}
			else{
				System.out.println("Upload File is not Successfull");

			}


			SoftAssert.assertAll();

		} catch (Exception e) {	
			e.printStackTrace();
			
		} finally {
		}}



	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();



	}


}
