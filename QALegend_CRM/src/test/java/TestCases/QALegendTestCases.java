package TestCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.asserts.*;

import AutomationCore.BaseClass;
import PageClasses.QALegend_ClientPage;
import PageClasses.QALegend_EventPage;
import PageClasses.QALegend_HomePage;
import PageClasses.QALegend_LoginPage;
import PageClasses.QALegend_MessagePage;
import PageClasses.QALegend_NotePage;
import PageClasses.QALegend_ProjectPage;
import PageClasses.QALegend_TicketsPage;
import PageClasses.QALegend_LeavePage;
import Utilities.ExcelUtility;
import Utilities.FakerUtility;

public class QALegendTestCases extends BaseClass
{
	public WebDriver driver;
	FileInputStream fis;
	Properties prop;
	QALegend_LoginPage login_Page; 
	QALegend_HomePage home_Page;
	QALegend_EventPage event_Page;
	QALegend_ClientPage client_Page;
	QALegend_MessagePage message_page;
	QALegend_TicketsPage ticket_Page;
	QALegend_LeavePage leave_Page;
	QALegend_ProjectPage project_Page;
	QALegend_NotePage note_Page;
	
	SoftAssert softassertion;
	
	
	@BeforeMethod
	@Parameters({"Browser"})
	public void initialization(String browser) throws Exception
	{
    driver = browserIntialization(browser);
    driver.manage().window().maximize();
    fis = new  FileInputStream("C:\\Users\\anagh\\git\\QA_Legend\\QA_Legend\\src\\main\\java\\TestData\\testdata.properties");
    prop = new Properties();
    prop.load(fis);
    softassertion =new SoftAssert();
    
    driver.get(prop.getProperty("url"));
    
    
    login_Page= new QALegend_LoginPage(driver);
    home_Page = new QALegend_HomePage(driver);
    event_Page = new QALegend_EventPage(driver);
    client_Page= new QALegend_ClientPage(driver);
    message_page= new QALegend_MessagePage(driver);
    ticket_Page =new QALegend_TicketsPage(driver);
    leave_Page= new QALegend_LeavePage(driver);
    project_Page =new QALegend_ProjectPage(driver);
    note_Page= new QALegend_NotePage(driver);
  
    
    login_Page.enterUserName(prop.getProperty("username"));
	login_Page.enterPassword(prop.getProperty("password"));
	login_Page.loginButton();
	}

	
	@Test(priority = 1)
	public void loginCRM()
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		org.testng.Assert.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
	}
	

	@Test (priority = 2)
	public void addEvent() throws IOException 
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		Assert.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		
		home_Page.clickOnEvents();
		Assert.assertEquals(event_Page.IsAddEventClickable(), true);
		
		event_Page.clickOnAddEvent();
		Assert.assertEquals(event_Page.addEventTitle(), prop.getProperty("titleEventPopUp"));
		
		String event_Title=ExcelUtility.getString(1, 0, excelFilePath, "Events")+FakerUtility.randomNumberCreation();
		event_Page.inputTitle(event_Title);
		event_Page.inputDescription(ExcelUtility.getString(1, 1, excelFilePath, "Events"));
		event_Page.inputStartDate();
		event_Page.selectStartDate();
		event_Page.inputEndDate();
		event_Page.selectEndDate();
		event_Page.inputLocation(ExcelUtility.getString(1, 4, excelFilePath, "Events"));
		//event_Page.inputLabel(ExcelUtility.getString(1, 5, excelFilePath, "Events"));
		//event_Page.inputClientField(ExcelUtility.getString(1, 6, excelFilePath, "Events"));
	    //event_Page.searchForClient(ExcelUtility.getString(1, 6, excelFilePath, "Events"));
		//event_Page.selectThemeColor();
		event_Page.clickOnSave();
		//assertion
		
	}
	@Test (priority = 3)
	public void addClient() throws IOException, InterruptedException
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		AssertJUnit.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		home_Page.clickOnClients();
		
		client_Page.clickOnAddClient();
		client_Page.inputAddress(ExcelUtility.getString(1, 1, excelFilePath, "Clients"));
		client_Page.inputCity(ExcelUtility.getString(1, 2, excelFilePath, "Clients"));
		client_Page.inputState(ExcelUtility.getString(1, 3, excelFilePath, "Clients"));
        client_Page.inputZip(ExcelUtility.getNumeric(1, 4, excelFilePath, "Clients"));
	    client_Page.inputCountry(ExcelUtility.getString(1, 5, excelFilePath, "Clients"));
	    client_Page.inputPhone(ExcelUtility.getNumeric(1, 6, excelFilePath, "Clients"));
	    client_Page.inputWebsite(ExcelUtility.getString(1, 7, excelFilePath, "Clients"));
	    client_Page.inputVAT(ExcelUtility.getNumeric(1, 8, excelFilePath, "Clients"));
	    client_Page.button_Save();
	    Assert.assertEquals(client_Page.mandatoryMessage(), prop.getProperty("fieldMandatoryMessage"));
	    client_Page.button_Save();
	    
	}
	@Test (priority = 4)
	public void downloadClientReport()
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		AssertJUnit.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		home_Page.clickOnClients();
		
		client_Page.downloadExcelReport();
		client_Page.printExcelReport();
		Assert.assertEquals(client_Page.noOfColumns(), 8);
		client_Page.switchParentTab();

	}
	@Test (priority = 5)
	public void sendEMail() throws IOException, AWTException
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		Assert.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		home_Page.clickOnMessage();	
		
		message_page.clickOnCompose();
		message_page.input_ToRecipient(ExcelUtility.getString(1, 0, excelFilePath, "Messages"));
		String subject =ExcelUtility.getString(1, 1, excelFilePath, "Messages")+FakerUtility.randomNumberCreation();
		message_page.input_Subject(subject);
		message_page.input_Message(ExcelUtility.getString(1, 2, excelFilePath, "Messages")+FakerUtility.randomNumberCreation());
		message_page.attachFile( ExcelUtility.getString(1, 3, excelFilePath, "Messages"));
		message_page.sendMessage();
		
		message_page.clickOnSentItems();
		Assert.assertEquals(message_page.isSubjectVisible(), true);
	}
	
	@Test (priority =6)
	public void verifyNumberOfTickets()
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		Assert.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		
		home_Page.noOfTickets();
		
		home_Page.clickOnTickets();
	    String displayedNoOfTickets=home_Page.noOfTickets();
		ticket_Page.clickOnPrint();
		String NoOfTicketsfFromTickets=ticket_Page.countTheNoOfTickets();
		ticket_Page.switchParentTab();
		Assert.assertEquals(NoOfTicketsfFromTickets, displayedNoOfTickets);
			
	}
	@Test(priority=7)
	public void addNotes() throws IOException
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		AssertJUnit.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		note_Page.clickOnNotesButton();
		note_Page.clickOnAddNotesButton();
		String expectedNotesTitle = note_Page.enterTitleInAddNotesPopUp(ExcelUtility.getString(1, 0, excelFilePath, "Notes"));
		note_Page.clickOnSave();
		
		String actualNotesTitle  = note_Page.getActualTitle();
		Assert.assertEquals(actualNotesTitle, expectedNotesTitle);

	}
	
	@Test(priority=8)
	public void leaveApplications()
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		Assert.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		
		home_Page.clickOnLeave();
		leave_Page.clickOnButtonApplyLeave();
		leave_Page.clickOnDropDown();
		leave_Page.selectCasualLeave();
		leave_Page.durationSingleDay();
		leave_Page.clickOnSingleDateField();
		leave_Page.selectDateOfLeave();
		leave_Page.input_Reason(prop.getProperty("leave_reason"));
		leave_Page.submitApplyLeave();
		
	}
	@Test (priority=9)
	public void statusOfProjects() throws IOException, AWTException, InterruptedException 
	{
		home_Page.logOut();
		login_Page.enterUserName(prop.getProperty("username"));
		login_Page.enterPassword(prop.getProperty("password"));
		login_Page.loginButton();
		AssertJUnit.assertEquals(home_Page.getUserProfileName(), prop.getProperty("user_profile_name"));
		 
		home_Page.clickOnProjects();
		home_Page.clickOnAllProjects();
		
		project_Page.clickOnAddProjectButton();
		String title=ExcelUtility.getString(1, 0, excelFilePath, "Projects");
		project_Page.inputTitle(title+FakerUtility.randomNumberCreation());
		project_Page.inputClient();
	    project_Page.selectFromDropDown(ExcelUtility.getString(1, 0, excelFilePath, "Clients"));
		project_Page.inputDescription(ExcelUtility.getString(1, 2, excelFilePath, "Projects"));
		project_Page.input_StartDate();
		project_Page.input_Deadline();
		project_Page.inputPrice(ExcelUtility.getNumeric(1, 5, excelFilePath, "Projects"));
		project_Page.saveProject();
			
		Assert.assertEquals(true,project_Page.addedProjectDisplayed() );
		
	}
	
	  @Test(priority =10)
	  
	  public void addTickets() throws IOException 
	  {
		  
	  home_Page.logOut();
	  login_Page.enterUserName(prop.getProperty("username"));
	  login_Page.enterPassword(prop.getProperty("password"));
	  login_Page.loginButton(); Assert.assertEquals(home_Page.getUserProfileName(),
	  prop.getProperty("user_profile_name"));
	  
	  home_Page.noOfTickets(); 
	  home_Page.clickOnTickets();
	  
	  ticket_Page.input_AddTicket(); 
	  ticket_Page.input_Title(ExcelUtility.getString(1, 0, excelFilePath, "Tickets"));
	  ticket_Page.inputClient(); 
	  ticket_Page.input_Description(ExcelUtility.getString(1, 3, excelFilePath, "Tickets"));
	  ticket_Page.clickOnSave();
	  ticket_Page.clickOnCancel();
	  
	  ticket_Page.clickOnFirstTicket();
	  
	  Assert.assertEquals(true, ticket_Page.visibilityActionButton());
	  Assert.assertEquals(true, ticket_Page.enabledActionButton());
	  
	  }
	 
	 

	
}


