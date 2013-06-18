package br.com.dextra.exchange_operations;

import org.openqa.selenium.By;

public class OperationPage extends BasePageObject {

	public void start() {
		open("index.html");
	}

	public void setValue(double value) {
		waitForElement(By.id("value")).sendKeys(String.valueOf(value));
	}

	public void setClient(String client) {
		waitForElement(By.id("client")).sendKeys(String.valueOf(client));
	}

	public void create() {
		waitForElement(By.id("submit")).click();
	}

	public void assertSuccess() {
		waitForElement(By.cssSelector(".message .success"));
	}
}
