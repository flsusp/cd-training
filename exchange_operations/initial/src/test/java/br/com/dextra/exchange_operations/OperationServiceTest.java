package br.com.dextra.exchange_operations;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;

public class OperationServiceTest extends BaseFunctionalTest {

	@Test
	public void testCreateOperation() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(100.0, Currency.USD, "123");
	}

	@Test
	public void testCreatedOperationByWeb() {
		OperationPage operation = new OperationPage();
		operation.start();
		operation.setValue(100.0);
		operation.setClient("123");
		operation.create();
		operation.assertSuccess();
	}

	public static void main(String[] args) throws Exception {
		startContainers();

		OperationPage operation = new OperationPage();

		Random random = new Random();

		while (true) {
			operation.start();
			operation.setValue(random.nextDouble() * 100.0);
			operation.setClient(RandomStringUtils.randomNumeric(10));
			operation.create();
			operation.assertSuccess();
		}
	}
}
