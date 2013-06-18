package br.com.dextra.sqlstore;

import java.util.List;

public class CurrentTimestampExpression extends Expression {

	@Override
	public String toString() {
		return "current_timestamp";
	}

	@Override
	protected void populateParameters(List<Object> parameters) {
	}
}
