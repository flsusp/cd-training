package br.com.dextra.exchange_operations.currency_converter;

import br.com.dextra.exchange_operations.Currency;

public interface CurrencyConverter {

	double convertToBRL(Currency currency, double value);
}
