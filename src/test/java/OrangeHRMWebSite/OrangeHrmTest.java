package OrangeHRMWebSite;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class OrangeHrmTest {
	

	public WebDriver driver;

	public String baserul = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

	private int i; 



	@BeforeTest
	public void DriverSetup() 
	{
		driver=new ChromeDriver();
		driver.get(baserul);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}


	public void login() {

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
	}


	public void logout() throws InterruptedException
	{
		Thread.sleep(1000);

		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")).click();
		driver.findElement(By.xpath("(//a[normalize-space()='Logout'])[1]")).click();  

		System.out.println("logout ");	

	}


	@Test (priority=1,  enabled=true)
	public void Logo () throws InterruptedException
	{
		Thread.sleep(1000);
		boolean status=driver.findElement(By.xpath("//img[@alt='company-branding']")).isDisplayed();
		System.out.println("logo Displayed.."+status);	  


	}




	@Test (priority=2, enabled=true)
	public void InvalidLogin() throws InterruptedException// Login with invalid credential 
	{
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");  
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin");
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();


		Thread.sleep(1000);
		String Conmessage =	driver.findElement(By.xpath("//p[text()='Invalid credentials']")).getText();

		if(Conmessage.contains("Invalid credentials")) {
			System.out.println("Invalid credentials");
		}else {
			System.out.println("Valid credentials "); 
		}


		driver.navigate().refresh();


	}


	@Test (priority=3, enabled=true)
	public void ValidLogin() throws InterruptedException  // Login with valid credential 
	{
		String  Username="Admin";
		String  Password="admin123";


		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(Username);
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(Password);
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

		String pageTitle = driver.getTitle(); 

		//		if(pageTitle.equals("OrangeHRM"))
		// 		{
		// 		   System.out.println("Login Success");
		// 		   
		// 		} 
		//		else {
		// 			System.out.println("Test failed ");
		// 		}


		AssertJUnit.assertEquals("OrangeHRM",  pageTitle); 		

		System.out.println("ValidLogin");

		Thread.sleep(1000);

		logout();

	}

	@Test  (priority=4, enabled=true)
	public void  PIM() throws InterruptedException {

		String FirstN ="tiger";
		String MiddleN ="last";
		String LastN ="last";

		login();

		//click on PIN menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		// click on add employee
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

		//enter a first name
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(FirstN);

		//enter a middle name
		driver.findElement(By.xpath("//input[@placeholder='Middle Name']")).sendKeys(MiddleN);

		//enter a last name
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(LastN);

		Thread.sleep(1000);
		//click on save button
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		String Conformmessage =	driver.findElement(By.xpath("//h6[text()='Personal Details']")).getText();

		if(Conformmessage.contains("Personal Details")) {
			System.out.println("Add Empolye Created");
		}else {
			System.out.println("Failed to Add Empolyee"); 
		}


		logout();

		//		Assert.assertEquals(Conformmessage,  "Personal Details"); 	

	}


	@Test  (priority=5, enabled=true)
	public void SearchByName() throws InterruptedException {


		String EmpName ="tiger";

		login();

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

		driver.findElements(By.tagName("input")).get(1).sendKeys(EmpName);

		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

		//    //span[@class='oxd-text oxd-text--span']
		Thread.sleep(5000)	;
		List<WebElement> element = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

		String expected_message = "Record Found";
		String message_actual = element.get(0).getText();
		System.out.println(message_actual);

		logout();

		AssertJUnit.assertTrue(message_actual.contains(expected_message));



		//		for (int i = 0 ; i<element.size(); i++)
		//		{
		//			System.out.println("At index "+ i + "text is :" + element.get(i).getText());  
		//		}


	}

	@Test(priority=6, enabled=false)
	public void SearchByID() throws InterruptedException {


		String empId = "301";
		String message_actual ="";

		login();

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

		//search by id
		driver.findElements(By.tagName("input")).get(2).sendKeys(empId);

		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

		Thread.sleep(2000)	;

		// Cast WebDriver to JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll down by a specific number of pixels
		js.executeScript("window.scrollBy(0,500)");

		Thread.sleep(5000)	;


		List<WebElement> rows = driver.findElements(By.xpath("(//div[@role='row'])"));
		if(rows.size()>1)
		{
			message_actual = driver.findElement(By.xpath("((//div[@role='row'])[2]/div[@role='cell'])[2]")).getText();
		}

		logout();
		AssertJUnit.assertEquals(empId, message_actual);


	}

	@Test(priority=7, enabled= false)
	public void FileUpload() throws InterruptedException {

		login();
		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();

		//click on Data Import
		driver.findElement(By.partialLinkText("Data")).click();

		//Click file upload 
		driver.findElement(By.cssSelector(".oxd-file-button")).sendKeys("/home/raghav/importData.csv");




		Thread.sleep(1000);


		//click on upload button
		driver.findElement(By.cssSelector("button[type='submit']")).click();				  

	}


	@Test (priority=8, enabled=true)
	public void DeleteEmployee() throws InterruptedException {

		login();

		String EmpName ="tiger";

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();

		driver.findElements(By.tagName("input")).get(1).sendKeys(EmpName);

		Thread.sleep(1000);
		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();

		// Cast WebDriver to JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll down by a specific number of pixels
		js.executeScript("window.scrollBy(0,500)");


		Thread.sleep(1000);
		//click on delete.......
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();

		Thread.sleep(3000);

		//Conform delete .......
		driver.findElement(By.xpath("//button[normalize-space()='Yes, Delete']")).click();
		Thread.sleep(5000);

		//check no record found.....
		driver.findElement(By.xpath("//span[normalize-space()='No Records Found']")).getText();


		String record ="No Records Found";

		if(record.contains("No Records Found")) {
			System.out.println("Employee deleted.. Successfully........ !" +record);
		}else {
			System.out.println(" Employ not found in below table.." ); 
		}


		logout();
	}

	@Test(priority=9, enabled=true)
	public void ListOfEmployee() throws InterruptedException {

		login();

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click(); 

		//find total links
		List<WebElement> totalLinksElements= driver.findElements(By.xpath("//ul[@class='oxd-pagination__ul']/li"));

		

	
		int totalLinks = totalLinksElements.size();
		
		for (int i=0; i<totalLinks; i++)//0,1,2,3,4
			
			System.out.println(totalLinks);

		{

			try
			{
				String currentLinkText = totalLinksElements.get(i).getText();


				int page = Integer.parseInt(currentLinkText);
				System.out.println("Page: " + page);

				totalLinksElements.get(i).click();

				Thread.sleep(2000);

				List <WebElement> emp_list = driver.findElements(By.xpath("//div[@class='oxd-table-card']/div /div[4]"));

				for(int j=0; j<emp_list.size();j++)
				{
					//print last name of each row 
					String lastName = emp_list.get(j).getText();
					System.out.println(lastName);
				}
			}
			catch(Exception e)
			{
				System.out.println("Not a number.");
			}


		}

		Thread.sleep(5000);
		logout();

	}
	
	
	@Test(priority=10, enabled=true)
	public void applyLeave() throws InterruptedException
	{
		//call login method
		login();
		
	Thread.sleep(1000);
		//click on leave menu
		driver.findElement(By.linkText("Leave")).click();
		
		//click on Apply menu
		driver.findElement(By.linkText("Apply")).click();
		
		//click on leave type drop down
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']")).click();
		
		//select CAN-FMLA option from leave type dropdown
		driver.findElement(By.xpath("//*[contains(text(),'CAN')]")).click();
		
		//enter from date
		driver.findElement(By.xpath("//div[@class='oxd-date-input']/input")).sendKeys("13-11-2024");
		
		
		//enter comment
		driver.findElement(By.tagName("textarea")).sendKeys("This is my personal leave");
		Thread.sleep(3000);
		
		
		//click on Apply button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		
		Thread.sleep(5000);
		
		logout();

	}




	@AfterTest
	public void BrowserClose() throws InterruptedException {

		Thread.sleep(1000);  //wait for 5 sec before quit test

		driver.close();
		driver.quit();
	}
	

}
