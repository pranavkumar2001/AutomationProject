package resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class Base {

	public static WebDriver driver;
	public Properties pro;
	
	public WebDriver invokeBrowser() throws IOException {

		this.loadDataPropertiesFile();
		String browserName = pro.getProperty("browser");
		if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("Firefox")) {
			System.getProperty("webdriver.gecko.driver",
					"C:\\Asha\\Selenium\\geckodriver-v0.23.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		System.out.println("**********************************************");
		System.out.println("Data taken from other branch is: "+getMailFromOtherBranch());
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return driver;

	}
	public void loadDataPropertiesFile() throws IOException {
		pro = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");

		pro.load(fis);
	}
	public String getWebsiteName() throws IOException {
		this.loadDataPropertiesFile();
		String websiteName = pro.getProperty("url");
		return websiteName;
	}

	private static String getMailFromOtherBranch()
	{
		String branchName = "pranav_wdr"; // Specify the branch you want to checkout
		String repoDir = System.getProperty("user.dir"); // Specify the path to your Git repository
		String filePath = "Email/email.txt"; // Path to the .txt file in the repo

		String email=null;
		try {
			// Construct the Git command
			String[] command = {"git", "show", branchName + ":" + filePath};

			// Create a process builder to execute the command
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process process = processBuilder.start();

			// Capture the command output
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			StringBuilder output = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			// Wait for the command to complete
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				// Print the file content
				email=output.toString();
			} else {
				System.err.println("Error: " + exitCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

}
