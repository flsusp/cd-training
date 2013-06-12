package br.com.dextra.sqlstore;


public class Name implements Field, TableName {

	private String name;

	public Name(String name) {
		super();
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}
}
