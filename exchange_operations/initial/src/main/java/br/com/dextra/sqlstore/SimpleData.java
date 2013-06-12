package br.com.dextra.sqlstore;

import java.util.HashMap;
import java.util.Map;

public class SimpleData implements Data {

	private Map<String, Value> values = new HashMap<String, Value>();

	public void put(String field, Object value) {
		values.put(field, Value.of(value));
	}

	@Override
	public boolean has(Field field) {
		return values.containsKey(field.name());
	}

	@Override
	public Value get(Field field) {
		if (has(field))
			return values.get(field.name());
		else
			throw new RuntimeException("Field with name " + field.name() + " not found.");
	}

	@Override
	public Value getOptional(Field field) {
		if (has(field))
			return values.get(field.name());
		else
			return new NullValue();
	}
}
