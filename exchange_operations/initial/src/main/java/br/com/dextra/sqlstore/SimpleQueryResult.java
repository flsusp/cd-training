package br.com.dextra.sqlstore;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleQueryResult implements QueryResult {

	private List<Data> result = new ArrayList<Data>();
	private Map<String, Value> generatedKeys = new HashMap<String, Value>();

	public Data createEntry() {
		SimpleData data = new SimpleData();
		result.add(data);
		return data;
	}

	@Override
	public Data unique() {
		if (result.isEmpty()) {
			throw new RuntimeException("To return an unique value the result cannot be empty.");
		}
		if (result.size() > 1) {
			throw new RuntimeException("To return an unique value the result must have just one element.");
		}
		return result.get(0);
	}

	@Override
	public Collection<Data> list() {
		return Collections.unmodifiableCollection(result);
	}

	@Override
	public Value getGeneratedKeyFor(Field field) {
		String key = field.name().toUpperCase();

		if (this.generatedKeys.containsKey(key)) {
			return this.generatedKeys.get(key);
		} else {
			return new NullValue();
		}
	}

	public void addGeneratedKey(String key, Object value) {
		this.generatedKeys.put(key.toUpperCase(), Value.of(value));
	}

	@Override
	public boolean isEmpty() {
		return result.isEmpty();
	}

	@Override
	public QueryResultArray toArray(Field field) {
		return new QueryResultArray(this, field);
	}
}
