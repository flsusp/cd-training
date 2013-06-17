package br.com.dextra.sqlstore.impl;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.dextra.sqlstore.Delete;
import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.Insert;
import br.com.dextra.sqlstore.Query;
import br.com.dextra.sqlstore.SQLQuery;
import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.TableName;
import br.com.dextra.sqlstore.Update;

public class JDBCStore implements SQLStore {

	private final Connection connection;

	public JDBCStore(Connection connection) {
		this.connection = connection;
	}

	@Override
	public SQLQuery sql(String sql) {
		return new JDBCSQLQuery(sql, connection, this);
	}

	@Override
	public SQLQuery sql(StringBuilder sql) {
		return sql(sql.toString());
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Query select(Field... fields) {
		return new JDBCQuery(connection, this).select(fields);
	}

	@Override
	public Insert create(TableName table) {
		return new JDBCInsert(table, this, connection);
	}

	@Override
	public void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void beginTransaction() {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Update update(TableName table) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Delete deleteFrom(TableName table) {
		throw new UnsupportedOperationException();
	}
}
