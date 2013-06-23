package br.com.dextra.exchange_operations;

public class Currency {

	private String code;

	public Currency(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public CurrencyCode getCurrencyCode() {
		return CurrencyCode.valueOf(code);
	}
}
