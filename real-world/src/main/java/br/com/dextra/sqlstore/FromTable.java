package br.com.dextra.sqlstore;


public class FromTable implements From {

	private TableName table;

	public FromTable(TableName table) {
		super();
		this.table = table;
	}

	@Override
	public String toString() {
		return table.name();
	}
}
