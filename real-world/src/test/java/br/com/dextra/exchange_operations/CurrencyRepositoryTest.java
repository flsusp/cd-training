package br.com.dextra.exchange_operations;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;

public class CurrencyRepositoryTest {

	@Test
	public void testBuscaPorCodigo() {
		CurrencyRepository repo = new CurrencyRepository(
				new BoneCPJDBCStoreService());

		assertEquals("USD", repo.findByCode("USD").getCode());
	}

}
