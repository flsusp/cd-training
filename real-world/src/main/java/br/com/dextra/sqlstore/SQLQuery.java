package br.com.dextra.sqlstore;


import java.util.List;

public abstract class SQLQuery extends Query {

	protected String sql;
	protected Object[] parameters;
	protected int limit = -1;
	protected int offset = -1;

	public SQLQuery(String sql, SQLStore datastore) {
		super(datastore);
		this.sql = sql;
	}

	public SQLQuery withParameters(List<Object> parameters) {
		return withParameters(parameters.toArray());
	}

	public SQLQuery withParameters(Object... parameters) {
		this.parameters = parameters;
		return this;
	}

	public SQLQuery limit(int limit) {
		this.limit = limit;
		return this;
	}

	public SQLQuery offset(int offset) {
		this.offset = offset;
		return this;
	}

	protected boolean hasLimitAndOffset() {
		return limit >= 0;
	}
}
