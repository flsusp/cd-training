package br.com.dextra.sqlstore.impl;

import java.sql.Connection;

import br.com.dextra.sqlstore.Query;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLStore;

public class JDBCQuery extends Query {

	private final Connection connection;

	public JDBCQuery(Connection connection, SQLStore sqlStore) {
		super(sqlStore);
		this.connection = connection;
	}

	@Override
	public QueryResult execute() {
		JDBCSQLQuery query = new JDBCSQLQuery(toString(), connection, datastore);
		return query.execute(getParameters());
	}
}
