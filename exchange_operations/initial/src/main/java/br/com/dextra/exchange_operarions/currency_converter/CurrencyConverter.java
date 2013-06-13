package br.com.dextra.exchange_operarions.currency_converter;

import br.com.dextra.exchange_operarions.Currency;

public interface CurrencyConverter {

	double convertToBRL(Currency currency, double value);
}
