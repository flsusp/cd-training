package br.com.dextra.exchange_operations;

import java.text.Normalizer;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

public class BasePageObject {

	private static final Logger logger = LoggerFactory.getLogger(BasePageObject.class);

	private static int TIMEOUT = 2;
	private static final int SLEEP = 100;

	protected static WebDriver driver;

	private final int port;

	public BasePageObject(int port) {
		this.port = port;
	}

	protected void open(String contextPath) {
		open(contextPath, By.tagName("body"));
	}

	protected void open(String contextPath, By conditionToExpect) {
		try {
			browser().navigate().to(buildURL(contextPath));
			waitForElement(conditionToExpect);
		} catch (RuntimeException e) {
			browser().navigate().to(buildURL(contextPath));
			waitForElement(conditionToExpect);
		}
	}

	protected String buildURL(String contextPath) {
		return "http://localhost:" + port + "/" + contextPath;
	}

	protected static boolean browserIsOpen() {
		return driver != null;
	}

	protected static WebDriver browser() {
		try {
			if (driver != null) {
				return driver;
			} else {
				closeBrowser();
				logger.warn("Browser inativo. Abrindo nova janela.");

				Proxy proxy = new Proxy();
				proxy.setAutodetect(false);
				proxy.setNoProxy("localhost");
				proxy.setHttpProxy("172.16.129.3:9090");

				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setJavascriptEnabled(true);
				caps.setCapability(CapabilityType.PROXY, proxy);
				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						"/home/fabio/bin/phantomjs-1.9.1-linux-x86_64/bin/phantomjs");
				driver = new PhantomJSDriver(caps);

				final WebDriver driverRef = driver;
				Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

					@Override
					public void run() {
						driverRef.close();
					}
				}));

				return driver;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void closeBrowser() {
		try {
			if (driver != null) {
				driver.close();
			}
		} finally {
			driver = null;
		}
	}

	protected By id(String id) {
		return By.id(id);
	}

	protected By xpath(String xpath) {
		return By.xpath(xpath);
	}

	protected void click(By element) {
		waitForElement(element).click();
	}

	protected void selectOptionValue(By by, String optionValue) {
		selectOptionByValue(by, optionValue);
	}

	protected void selectOptionByValue(final By by, final String value) {
		final Select select = new Select(waitForElement(by));
		waitUntil(new Predicate<WebDriver>() {
			public boolean apply(WebDriver input) {
				List<WebElement> options = select.getOptions();
				for (WebElement option : options) {
					if (option.getAttribute("value").equals(value)) {
						select.selectByValue(value);
						return true;
					}
				}
				return false;
			}
		});
	}

	protected String getSelectedValueFor(By by) {
		return new Select(waitForElement(by)).getFirstSelectedOption().getAttribute("value");
	}

	protected String getAttribute(By element, String attributeName) {
		return waitForElement(element).getAttribute(attributeName);
	}

	protected int count(By filter) {
		return browser().findElements(filter).size();
	}

	public Object executeJS(String js) {
		ScriptRun script = new ScriptRun(js);

		try {
			waitUntil(script);
		} catch (StaleElementReferenceException e) {
			waitUntil(script);
		}

		return script.getResult();
	}

	protected boolean isElementDisplayed(By by) {
		try {
			WebElement element = browser().findElement(by);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected WebElement waitForLinkWithText(String text) {
		return waitForElement(xpath("//a[text()='" + text + "'] | //a//*[text()='" + text + "']"));
	}

	protected WebElement waitForElementText(final By by, String value) {
		ElementTextIs condition = new ElementTextIs(by, value);
		try {
			waitUntil(wrapForAjaxRequestControl(condition));
		} catch (StaleElementReferenceException e) {
			waitForElementText(by, value);
		}
		return condition.elementFound;
	}

	protected List<WebElement> waitForElements(final By by) {
		final ListOfElementsExists condition = new ListOfElementsExists(by);

		try {
			waitUntil(wrapForAjaxRequestControl(condition));
		} catch (TimeoutException e) {
			throw new RuntimeException("timeout while waiting for " + by, e);
		} catch (StaleElementReferenceException e) {
			return waitForElements(by);
		}

		return condition.elementsFound;
	}

	protected WebElement waitForElement(final By by) {
		final ElementExists condition = new ElementExists(by);

		try {
			waitUntil(wrapForAjaxRequestControl(condition));
		} catch (TimeoutException e) {
			throw new RuntimeException("timeout while waiting for " + by, e);
		} catch (StaleElementReferenceException e) {
			return waitForElement(by);
		}

		return condition.found;
	}

	protected ExistanceCondition wrapForAjaxRequestControl(final ExistanceCondition condition) {
		return condition;
	}

	protected void waitUntil(Predicate<WebDriver> predicate) {
		getWait().until(predicate);
	}

	protected void waitUntil(ExistanceCondition condition) {
		try {
			getWait().until(condition);
		} catch (TimeoutException e) {
			getWait().until(condition);
		}

		if (!condition.applied()) {
			throw new NoSuchElementException("Element could not be found in " + TIMEOUT + " seconds.");
		}
	}

	private WebDriverWait getWait() {
		return new WebDriverWait(browser(), TIMEOUT, SLEEP);
	}

	protected String removeAccents(String text) {
		if (text == null)
			return null;
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	protected WebElement parentOf(WebElement el) {
		return el.findElement(By.xpath(".."));
	}

	protected void fill(By by, String text) {
		WebElement el = waitForElement(by);
		el.clear();
		el.sendKeys(text);
	}

	protected interface ExistanceCondition extends ExpectedCondition<Boolean> {
		boolean applied();
	}

	protected class ScriptRun implements ExistanceCondition {

		protected String script;
		protected boolean success = false;
		protected Object result;

		public ScriptRun(String script) {
			this.script = script;
		}

		@Override
		public Boolean apply(WebDriver input) {
			try {
				this.result = ((JavascriptExecutor) browser()).executeScript(script);
				this.success = true;
			} catch (Exception e) {
			}
			return applied();
		}

		@Override
		public boolean applied() {
			return success;
		}

		public Object getResult() {
			return result;
		}

		@Override
		public String toString() {
			return "ScriptRun [script=" + script + "]";
		}
	}

	protected final class ElementExists implements ExistanceCondition {
		WebElement found;
		private final By by;

		public ElementExists(By by) {
			this.by = by;
		}

		@Override
		public Boolean apply(WebDriver driver) {
			found = driver.findElement(by);
			return applied();
		}

		@Override
		public boolean applied() {
			return found != null && found.isDisplayed();
		}

		@Override
		public String toString() {
			return "ElementExists [found=" + found + ", by=" + by + "]";
		}
	}

	protected final class ListOfElementsExists implements ExistanceCondition {

		List<WebElement> elementsFound;
		private final By by;

		public ListOfElementsExists(By by) {
			this.by = by;
		}

		@Override
		public Boolean apply(WebDriver driver) {
			elementsFound = driver.findElements(by);
			return applied();
		}

		@Override
		public boolean applied() {
			if (elementsFound == null)
				return false;

			for (WebElement el : elementsFound) {
				if (el.isDisplayed()) {
					return true;
				}
			}

			return false;
		}

		@Override
		public String toString() {
			return "ListOfElementsExists [elementsFound=" + elementsFound + ", by=" + by + "]";
		}
	}

	protected final class ElementTextIs implements ExistanceCondition {
		private final By by;
		private String value;
		protected String valueFound;
		protected WebElement elementFound;

		public ElementTextIs(By by, String value) {
			this.by = by;
			this.value = value;
			if (this.value == null) {
				this.value = "";
			}
			this.value = removeAccents(this.value).toUpperCase();
		}

		@Override
		public Boolean apply(WebDriver driver) {
			List<WebElement> elements = driver.findElements(by);
			if (elements == null || elements.isEmpty()) {
				return false;
			}

			for (WebElement el : elements) {
				String text = el.getText();
				text = removeAccents(text).toUpperCase();
				this.valueFound = text;
				this.elementFound = el;
				if (applied()) {
					return true;
				}
			}

			return false;
		}

		@Override
		public boolean applied() {
			return this.value.equals(this.valueFound);
		}

		@Override
		public String toString() {
			return "ElementTextIs [by=" + by + ", value=" + value + ", valueFound=" + valueFound + ", elementFound="
					+ elementFound + "]";
		}
	}
}
