package br.com.dextra.exchange_operations;

import org.junit.Test;

import br.com.dextra.exchange_operations.Currency;
import br.com.dextra.exchange_operations.OperationService;
import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;

public class OperationServiceTest {

	@Test
	public void testCreateOperation() {
		// Please, be condescending, we are just testing if nothing fails
		OperationService service = new OperationService(new BoneCPJDBCStoreService());
		service.createOperation(100.0, Currency.USD, "123");
	}
}
