package com.finance;

import com.elementDetails.LoginDetails;
import androidMain.AndroidTestBase;
import java.net.MalformedURLException;

public class FinancialManager {

	public static void main(String[] args) throws InterruptedException {
		try {
			AndroidTestBase.setUp();
			Thread.sleep(3000);
			LoginDetails.userName().sendKeys("Xavier");
			LoginDetails.passWord().sendKeys("Dominic");
			LoginDetails.loginButton().click();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error occured:"+e.toString());
		}
	}
}
