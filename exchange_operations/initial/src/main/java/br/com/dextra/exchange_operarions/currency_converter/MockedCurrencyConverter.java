package br.com.dextra.exchange_operarions.currency_converter;

import br.com.dextra.exchange_operations.Currency;

public class MockedCurrencyConverter implements CurrencyConverter {

	@Override
	public double convertToBRL(Currency currency, double value) {
		return value * 2;
	}
}
