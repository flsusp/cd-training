package br.com.dextra.exchange_operations.cash_register;

import br.com.dextra.exchange_operations.CurrencyCode;

public interface CashRegister {

	void registerDebt(CurrencyCode currency, Double value);

	void registerCredit(CurrencyCode currency, Double value);
}
