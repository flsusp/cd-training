package br.com.dextra.sqlstore;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Update {

	protected final SQLStore datastore;
	protected final TableName table;
	private List<Entry<Field, Expression>> fieldsToUpdate = new ArrayList<Entry<Field, Expression>>();
	private Query whereQuery;
	private Condition whereCondition;

	public Update(TableName table, SQLStore datastore) {
		super();
		this.table = table;
		this.datastore = datastore;
		this.whereQuery = new Query(datastore) {

			@Override
			public SimpleQueryResult execute() {
				Update.this.execute();
				return new SimpleQueryResult();
			}

			@Override
			public String toString() {
				return Update.this.toString();
			}
		};
	}

	public Update set(Field field, Object value) {
		fieldsToUpdate.add(new ListEntry<Field, Expression>(field, new ValueExpression(value)));
		return this;
	}

	public Update set(Field field, Field fieldValue) {
		fieldsToUpdate.add(new ListEntry<Field, Expression>(field, new FieldExpression(fieldValue)));
		return this;
	}

	public Update setNull(Field field) {
		fieldsToUpdate.add(new ListEntry<Field, Expression>(field, ValueExpression.Null));
		return this;
	}

	public Update setCurrentTimestamp(Field field) {
		fieldsToUpdate.add(new ListEntry<Field, Expression>(field, new CurrentTimestampExpression()));
		return this;
	}

	protected Object[] getParameters() {
		List<Object> parameters = new ArrayList<Object>();

		Iterator<Entry<Field, Expression>> iterator = this.fieldsToUpdate.iterator();
		while (iterator.hasNext()) {
			Entry<Field, Expression> entry = iterator.next();
			entry.getValue().populateParameters(parameters);
		}

		if (this.whereCondition != null) {
			this.whereCondition.populateParameters(parameters);
		}
		return parameters.toArray();
	}

	public abstract void execute();

	public void executeAndClose() {
		try {
			execute();
		} finally {
			datastore.close();
		}
	}

	public Condition where(Field field) {
		whereCondition = this.whereQuery.and(field);
		return whereCondition;
	}

	public Condition where(Object value) {
		whereCondition = this.whereQuery.and(value);
		return whereCondition;
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(this.table.name());
		sql.append(" SET ");

		Iterator<Entry<Field, Expression>> iterator = this.fieldsToUpdate.iterator();
		while (iterator.hasNext()) {
			Entry<Field, Expression> entry = iterator.next();
			sql.append(entry.getKey().name());
			sql.append(" = ");
			sql.append(entry.getValue().toString());
			if (iterator.hasNext()) {
				sql.append(",");
			}
			sql.append(" ");
		}

		if (this.whereCondition != null) {
			sql.append("WHERE ");
			sql.append(this.whereCondition.toString());
		}

		return sql.toString();
	}

	private static final class ListEntry<K, V> implements Map.Entry<K, V> {

		private K key;
		private V value;

		public ListEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}
	}
}
