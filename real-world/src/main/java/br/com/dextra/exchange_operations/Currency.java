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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
