package br.com.dextra.sqlstore.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.Insert;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.SimpleQueryResult;
import br.com.dextra.sqlstore.TableName;

import com.google.gson.JsonElement;

public class JDBCInsert extends Insert {

	private final Connection connection;

	public JDBCInsert(TableName table, SQLStore datastore, Connection connection) {
		super(table, datastore);
		this.connection = connection;
	}

	@Override
	public QueryResult execute() {
		List<Object> parameters = new ArrayList<Object>();

		StringBuilder sb0 = new StringBuilder("(");
		StringBuilder sb1 = new StringBuilder("(");

		Set<Entry<Field, Object>> entrySet = getValues().entrySet();
		for (Entry<Field, Object> entry : entrySet) {
			sb0.append(entry.getKey().name());
			sb0.append(",");

			sb1.append("?");
			sb1.append(",");

			parameters.add(entry.getValue());
		}
		sb0 = new StringBuilder(sb0.substring(0, sb0.length() - 1));
		sb1 = new StringBuilder(sb1.substring(0, sb1.length() - 1));

		sb0.append(")");
		sb1.append(")");

		StringBuilder sqlBuilder = new StringBuilder("INSERT into ");
		sqlBuilder.append(table.name());
		sqlBuilder.append(" ");
		sqlBuilder.append(sb0.toString());
		sqlBuilder.append(" VALUES ");
		sqlBuilder.append(sb1.toString());
		String sql = sqlBuilder.toString();

		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setStatementParameters(parameters, prepareStatement);
			prepareStatement.execute();

			SimpleQueryResult result = new SimpleQueryResult();

			Integer columnCount = null;
			ResultSet rs = prepareStatement.getGeneratedKeys();
			boolean next = rs.next();
			if (next) {
				if (columnCount == null) {
					columnCount = rs.getMetaData().getColumnCount();
				}

				for (int i = 1; i <= columnCount; i++) {
					result.addGeneratedKey(rs.getMetaData().getColumnName(i).toUpperCase(), rs.getObject(i));
				}
			}

			rs.close();
			prepareStatement.close();

			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void setStatementParameters(List<Object> parameters, PreparedStatement prepareStatement)
			throws SQLException {
		int i = 1;

		for (Object parameter : parameters) {
			setStatementParameter(prepareStatement, i++, parameter);
		}
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
}
