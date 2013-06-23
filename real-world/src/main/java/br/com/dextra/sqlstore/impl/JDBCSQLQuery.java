package br.com.dextra.sqlstore.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLQuery;
import br.com.dextra.sqlstore.SQLStore;

import com.google.gson.JsonElement;

public class JDBCSQLQuery extends SQLQuery {

	private final Connection connection;

	public JDBCSQLQuery(String sql, Connection connection, SQLStore datastore) {
		super(sql, datastore);
		this.connection = connection;
	}

	@Override
	public QueryResult execute() {
		return execute(getParameters());
	}

	private void setStatementParameter(PreparedStatement prepareStatement, int i, Object value) throws SQLException {
		if (value instanceof Timestamp) {
			prepareStatement.setTimestamp(i, (Timestamp) value, Calendar.getInstance());
		} else if (Date.class.isInstance(value)) {
			prepareStatement.setTimestamp(i, new java.sql.Timestamp(((Date) value).getTime()), Calendar.getInstance());
		} else if (JsonElement.class.isInstance(value)) {
			prepareStatement.setString(i, value.toString());
		} else if (String.class.isInstance(value)) {
			prepareStatement.setString(i, (String) value);
		} else {
			prepareStatement.setObject(i, value);
		}
	}

	public QueryResult execute(Object[] params) {
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				setStatementParameter(stmt, i + 1, params[i]);
			}

			ResultSet rs = stmt.executeQuery();

			return new JDBCQueryResult(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
