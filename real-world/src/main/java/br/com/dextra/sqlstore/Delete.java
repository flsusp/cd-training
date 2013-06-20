package br.com.dextra.sqlstore;


import java.util.ArrayList;
import java.util.List;

public abstract class Delete {

	protected final SQLStore datastore;
	protected final TableName table;
	private Query whereQuery;

	public Delete(TableName table, SQLStore datastore) {
		super();
		this.table = table;
		this.datastore = datastore;
		this.whereQuery = new Query(datastore) {

			@Override
			public SimpleQueryResult execute() {
				Delete.this.execute();
				return new SimpleQueryResult();
			}

			@Override
			public String toString() {
				return Delete.this.toString();
			}
		};
	}

	protected Object[] getParameters() {
		List<Object> parameters = new ArrayList<Object>();

		if (this.whereQuery != null) {
			this.whereQuery.where.populateParameters(parameters);
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
		return this.whereQuery.and(field);
	}

	public Condition where(Object value) {
		return this.whereQuery.and(value);
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ");
		sql.append(this.table.name());

		if (this.whereQuery != null) {
			sql.append(" WHERE ");
			sql.append(this.whereQuery.where.toString());
		}

		return sql.toString();
	}
}
