package br.com.dextra.sqlstore;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Insert {

	protected final SQLStore datastore;
	protected final TableName table;
	private Map<Field, Object> values = new HashMap<Field, Object>();

	public Insert(TableName table, SQLStore datastore) {
		super();
		this.table = table;
		this.datastore = datastore;
	}

	public Insert with(Field field, Object value) {
		values.put(field, value);
		return this;
	}

	protected Map<Field, Object> getValues() {
		return Collections.unmodifiableMap(values);
	}

	public abstract QueryResult execute();

	public QueryResult executeAndClose() {
		try {
			return execute();
		} finally {
			datastore.close();
		}
	}
}
