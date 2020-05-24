package com.elementDetails;

import org.openqa.selenium.By;

import androidMain.AndroidTestBase;
import io.appium.java_client.MobileElement;

public class RegisterDetails extends AndroidTestBase {
	
	public static MobileElement userName() {
		return driver.findElement(By.id("com.myapp.finance:id/user"));
	}
	
	public static MobileElement register_passWord() {
		return driver.findElement(By.id("com.myapp.finance:id/pass"));
	}
	
	public static MobileElement register_confirm_Password() {
		return driver.findElement(By.id("com.myapp.finance:id/repass"));
	}
	
	public static MobileElement mobileNumber() {
		return driver.findElement(By.id("com.myapp.finance:id/mob_num"));
	}
	
	public static MobileElement registerButton() {
		return driver.findElement(By.id("com.myapp.finance:id/create"));
	}
	
	public static MobileElement backButton() {
		return driver.findElement(By.id("com.myapp.finance:id/cancel"));
	}
	
}