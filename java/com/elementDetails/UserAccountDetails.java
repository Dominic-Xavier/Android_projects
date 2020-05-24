package com.elementDetails;

import org.openqa.selenium.By;

import androidMain.AndroidTestBase;
import io.appium.java_client.MobileElement;

public class UserAccountDetails extends AndroidTestBase {
	
	public static MobileElement setProfilePic() {
		return driver.findElement(By.id("com.myapp.finance:id/profile_pic_button"));
	}
	
	public static MobileElement Close() {
		return driver.findElement(By.id("com.myapp.finance:id/quit"));
	}
}
