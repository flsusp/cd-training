package br.com.dextra.sqlstore.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import br.com.dextra.sqlstore.Data;
import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.QueryResultArray;
import br.com.dextra.sqlstore.Value;

public class JDBCQueryResult implements QueryResult {

	private final ResultSet resultSet;

	public JDBCQueryResult(ResultSet resultSet) {
		super();
		this.resultSet = resultSet;
	}

	@Override
	public Data unique() {
		try {
			this.resultSet.next();
			return new ResultSetData(this.resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Data> list() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value getGeneratedKeyFor(Field field) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		try {
			return this.resultSet.last();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public QueryResultArray toArray(Field field) {
		throw new UnsupportedOperationException();
	}
}
