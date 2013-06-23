package br.com.dextra.exchange_operations.currency_converter;

import br.com.dextra.exchange_operations.CurrencyCode;

public class MockedCurrencyConverter implements
		CurrencyConverter {

	@Override
	public double convertToBRL(CurrencyCode currency,
			double value) {
		if (currency == CurrencyCode.USD)
			return value * 2;
		else
			return value * 3;
	}
}
