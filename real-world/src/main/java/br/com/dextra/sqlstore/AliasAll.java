package br.com.dextra.sqlstore;


public class AliasAll implements Field {

	private String alias;

	public AliasAll(String alias) {
		super();
		this.alias = alias;
	}

	@Override
	public String name() {
		StringBuilder s = new StringBuilder();
		s.append(alias);
		s.append(".*");
		return s.toString();
	}
}
