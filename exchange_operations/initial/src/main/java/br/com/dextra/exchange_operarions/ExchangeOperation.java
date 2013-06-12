package br.com.dextra.exchange_operarions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeOperation {

	private static final Logger logger = LoggerFactory.getLogger(ExchangeOperation.class);

	private final String clientDocument;
	private Double value;
	private Currency currency;

	public ExchangeOperation(String clientDocument) {
		this.clientDocument = clientDocument;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void exchange() {
		logger.info("Exchanging {} {} for {}.", new Object[] { currency, value, clientDocument });
	}
}
