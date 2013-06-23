package br.com.dextra.exchange_operations.cash_register;

import br.com.dextra.exchange_operations.Currency;

public interface CashRegister {

	void registerDebt(Currency currency, Double value);

	void registerCredit(Currency currency, Double value);
}
