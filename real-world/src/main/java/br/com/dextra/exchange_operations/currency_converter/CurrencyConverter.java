package br.com.dextra.exchange_operations.currency_converter;

import br.com.dextra.exchange_operations.CurrencyCode;

public interface CurrencyConverter {

	double convertToBRL(CurrencyCode currency, double value);
}
