package br.com.dextra.exchange_operarions.cash_register;

import br.com.dextra.exchange_operarions.Currency;

public interface CashRegister {

	void registerDebt(Currency currency, Double value);

	void registerCredit(Currency currency, Double value);
}
