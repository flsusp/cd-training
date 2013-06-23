package br.com.dextra.exchange_operations;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;
import br.com.dextra.test.BaseFunctionalTest;

public class OperationServiceTest extends BaseFunctionalTest {

	@Test
	public void testCreateOperationUSD() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(100.0, Currency.USD, "123");
	}

	@Test
	public void testCreateOperationEUR() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(300.0, Currency.EUR, "123");
	}

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
		operation.setCurrency(Currency.USD);
		operation.setValue(100.0);
		operation.setClient("123");
		operation.create();
		operation.assertSuccess();
	}

	@Test
	public void testCreatedOperationByWebEUR() {
		OperationPage operation = new OperationPage(8080);
		operation.start();
		operation.setCurrency(Currency.EUR);
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
