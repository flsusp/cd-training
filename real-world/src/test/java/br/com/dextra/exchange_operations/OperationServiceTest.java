package br.com.dextra.exchange_operations;

import org.junit.Test;

import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;
import br.com.dextra.test.BaseFunctionalTest;

public class OperationServiceTest extends BaseFunctionalTest {

	@Test
	public void testCreateOperationUSD() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(100.0, "USD", "123");
	}

	@Test
	public void testCreateOperationEUR() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(300.0, "EUR", "123");
	}

}
