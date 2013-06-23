package br.com.dextra.exchange_operations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.test.BasePageObject;

public class OperationPage extends BasePageObject {

	public OperationPage(int port) {
		super(port);
	}

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
		waitForElement(By.cssSelector(".alert-success"));
	}

	public void setCurrency(Currency currency) {
		WebElement element = waitForElement(By.id("currency"));
		element.clear();
		element.sendKeys(String.valueOf(currency));
	}
}
