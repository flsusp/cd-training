package br.com.dextra.exchange_operations;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import br.com.dextra.test.BaseFunctionalTest;

public class OperationServiceFunctionalTest extends BaseFunctionalTest {

	@Test
	public void testCreatedOperationByWebWithoutCurrrency() {
		OperationPage operation = new OperationPage(8080);
		operation.start();
		operation.setValue(100.0);
		operation.setClient("123");
		operation.create();
		operation.assertSuccess();
	}

	@Test
	public void testCreatedOperationByWebUSD() {
		OperationPage operation = new OperationPage(8080);
		operation.start();
		operation.setCurrency("USD");
		operation.setValue(100.0);
		operation.setClient("123");
		operation.create();
		operation.assertSuccess();
	}

	@Test
	public void testCreatedOperationByWebEUR() {
		OperationPage operation = new OperationPage(8080);
		operation.start();
		operation.setCurrency("EUR");
		operation.setValue(300.0);
		operation.setClient("123");
		operation.create();
		operation.assertSuccess();
	}

	public static void main(String[] args) throws Exception {
		OperationPage operation = new OperationPage(80);

		Random random = new Random();

		while (true) {
			try {
				operation.start();
				operation.setValue(random.nextDouble() * 100.0);
				operation.setClient(RandomStringUtils.randomNumeric(10));
				operation.create();
				operation.assertSuccess();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Thread.sleep(1000);
		}
	}

}
