package br.com.dextra.exchange_operations.currency_converter;

import br.com.dextra.exchange_operations.Currency;

public class MockedCurrencyConverter implements
		CurrencyConverter {

	@Override
	public double convertToBRL(Currency currency,
			double value) {
		if (currency == Currency.USD)
			return value * 2;
		else
			return value * 3;
	}
}
