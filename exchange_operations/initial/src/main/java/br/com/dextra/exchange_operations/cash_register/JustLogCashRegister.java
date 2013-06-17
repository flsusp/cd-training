package br.com.dextra.exchange_operations.cash_register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.exchange_operations.Currency;

public class JustLogCashRegister implements CashRegister {

	private static final Logger logger = LoggerFactory.getLogger(JustLogCashRegister.class);

	@Override
	public void registerDebt(Currency currency, Double value) {
		logger.info("DEBT   {} {}", currency, value);
	}

	@Override
	public void registerCredit(Currency currency, Double value) {
		logger.info("CREDIT {} {}", currency, value);
	}
}
