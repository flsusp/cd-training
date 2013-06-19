package br.com.dextra.sqlstore.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dextra.sqlstore.Data;
import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.Value;

public class ResultSetData implements Data {

	private final ResultSet resultSet;

	public ResultSetData(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	@Override
	public boolean has(Field field) {
		try {
			this.resultSet.findColumn(field.name().toUpperCase());
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Value get(Field field) {
		try {
			return Value.of(this.resultSet.getObject(field.name().toUpperCase()));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Value getOptional(Field field) {
		try {
			return Value.of(this.resultSet.getObject(field.name().toUpperCase()));
		} catch (SQLException e) {
			return null;
		}
	}
}
