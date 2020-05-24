package com.elementDetails;

import org.openqa.selenium.By;

import androidMain.AndroidTestBase;
import io.appium.java_client.MobileElement;

public class DatabaseElements extends AndroidTestBase {
	
	public static MobileElement optionSelect() {
		return driver.findElement(By.id("com.myapp.finance:id/spinner"));
	}
	
	public static MobileElement option_Expense() {
		return driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]"));
	}
	
	public static MobileElement option_Income() {
		return driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[3]"));
	}
	
	public static MobileElement addRow() {
		return driver.findElement(By.id("com.myapp.finance:id/add"));
	}
	
	public static MobileElement deleteRow() {
		return driver.findElement(By.id("com.myapp.finance:id/delete"));
	}
	
	public static MobileElement saveData() {
		return driver.findElement(By.id("com.myapp.finance:id/ins"));
	}
	
	public static MobileElement displayData() {
		return driver.findElement(By.id("com.myapp.finance:id/ret"));
	}
	
	public static MobileElement description(int rowCount) {
		return driver.findElement(By.xpath("/hiera/hierrchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.TableRow["+rowCount+"]/android.widget.EditText[1])"));
	}
	
	public static MobileElement amount(int rowCount) {
		return driver.findElement(By.xpath("/hiera/hierrchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.ScrollView/android.widget.TableLayout/android.widget.TableRow["+rowCount+"]/android.widget.EditText[2])"));
	}
}
