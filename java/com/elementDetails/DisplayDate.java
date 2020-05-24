package com.elementDetails;

import org.openqa.selenium.By;

import androidMain.AndroidTestBase;
import io.appium.java_client.MobileElement;

public class DisplayDate extends AndroidTestBase {
	
	public static MobileElement startDate() {
		return driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText[1]"));
	}
	
	public static MobileElement endDate() {
		return driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText[2]"));
	}
	
	public static MobileElement canncel() {
		return driver.findElement(By.id("android:id/button2"));
	}
	
	public static MobileElement okButton() {
		return driver.findElement(By.id("android:id/button1"));
	}
	
	
	
}
