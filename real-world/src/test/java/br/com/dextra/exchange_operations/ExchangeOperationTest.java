package br.com.dextra.exchange_operations;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.dextra.exchange_operations.CurrencyCode;
import br.com.dextra.exchange_operations.ExchangeOperation;
import br.com.dextra.exchange_operations.cash_register.CashRegister;
import br.com.dextra.exchange_operations.currency_converter.MockedCurrencyConverter;

public class ExchangeOperationTest {

	@Test
	public void testExchangeUSD() {
		ExchangeOperation operation = new ExchangeOperation(
				"123");
		operation.setCurrency(new Currency("USD"));
		operation.setValue(100.0);

		MockedCashRegister cashRegister = new MockedCashRegister();

		operation.exchange(cashRegister,
				new MockedCurrencyConverter());

		assertEquals(1, cashRegister.debts.size());
		assertEquals(CurrencyCode.USD,
				cashRegister.debts.get(0).currency);
		assertEquals(100.0,
				cashRegister.debts.get(0).value, 0.01);

		assertEquals(1, cashRegister.credits.size());
		assertEquals(CurrencyCode.BRL,
				cashRegister.credits.get(0).currency);
		assertEquals(200.0,
				cashRegister.credits.get(0).value, 0.01);
	}

	@Test
	public void testExchangeEUR() {
		ExchangeOperation operation = new ExchangeOperation(
				"123");
		operation.setCurrency(new Currency("EUR"));
		operation.setValue(100.0);

		MockedCashRegister cashRegister = new MockedCashRegister();

		operation.exchange(cashRegister,
				new MockedCurrencyConverter());

		assertEquals(1, cashRegister.debts.size());
		assertEquals(CurrencyCode.EUR,
				cashRegister.debts.get(0).currency);
		assertEquals(100.0,
				cashRegister.debts.get(0).value, 0.01);

		assertEquals(1, cashRegister.credits.size());
		assertEquals(CurrencyCode.BRL,
				cashRegister.credits.get(0).currency);
		assertEquals(300.0,
				cashRegister.credits.get(0).value, 0.01);
	}

	private static final class MockedCashRegister implements
			CashRegister {

		private List<Moviment> debts = new ArrayList<>();
		private List<Moviment> credits = new ArrayList<>();

		private static final class Moviment {

			private CurrencyCode currency;
			private Double value;

			public Moviment(CurrencyCode currency,
					Double value) {
				super();
				this.currency = currency;
				this.value = value;
			}
		}

		@Override
		public void registerDebt(Currency currency,
				Double value) {
			debts.add(new Moviment(currency
					.getCurrencyCode(), value));
		}

		@Override
		public void registerCredit(Currency currency,
				Double value) {
			credits.add(new Moviment(currency
					.getCurrencyCode(), value));
		}
	}
}
