package br.com.dextra.exchange_operarions;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.exchange_operarions.cash_register.CashRegister;
import br.com.dextra.exchange_operarions.currency_converter.CurrencyConverter;

public class ExchangeOperation {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeOperation.class);

	private final String clientDocument;
	private final Date creationDate;
	private Double value;
	private Currency currency;
	private Long id;

	public ExchangeOperation(String clientDocument) {
		this.clientDocument = clientDocument;
		this.creationDate = new Date();
	}

	public void exchange(CashRegister cashRegister, CurrencyConverter currencyConverter) {
		logger.info("Purchase of {} {} for {}.", new Object[] { currency, value, clientDocument });

		double value = currencyConverter.convertToBRL(getCurrency(), getValue());

		cashRegister.registerCredit(Currency.BRL, value);
		cashRegister.registerDebt(getCurrency(), getValue());
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	protected String getClientDocument() {
		return clientDocument;
	}

	protected Date getCreationDate() {
		return creationDate;
	}

	protected Double getValue() {
		return value;
	}

	protected Currency getCurrency() {
		return currency;
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
}
