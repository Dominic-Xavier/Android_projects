package androidMain;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidTestBase {
	
	public static AppiumDriver <MobileElement> driver;
	
	public static void setUp() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "vivo 1951");
		cap.setCapability("udId", "f0d81e23");
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion","9");
		cap.setCapability("appPackage", "com.myapp.finance");
		cap.setCapability("appActivity", "com.myapp.finance.Database");
		cap.setCapability("automationName", "UIautomator1");
		
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		
		System.out.println("Application started");
		
	}
}
