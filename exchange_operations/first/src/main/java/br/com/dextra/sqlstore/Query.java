package br.com.dextra.sqlstore;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Query {

	protected final SQLStore datastore;
	private List<Field> selectFields = new ArrayList<Field>();
	private From from;
	protected Condition where = new EmptyCondition(this);
	private List<Expression> orderBy = new ArrayList<Expression>();
	private int limit = -1;

	public Query(SQLStore datastore) {
		super();
		this.datastore = datastore;
	}

	public abstract QueryResult execute();

	public QueryResult executeAndClose() {
		try {
			return execute();
		} finally {
			datastore.close();
		}
	}

	public String toString() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT");
		if (selectFields.isEmpty()) {
			sql.append(" *");
		} else {
			for (int i = 0; i < selectFields.size() - 1; i++) {
				sql.append(" ");
				sql.append(selectFields.get(i).name());
				sql.append(",");
			}
			sql.append(" ");
			sql.append(selectFields.get(selectFields.size() - 1).name());
		}

		if (from != null) {
			sql.append(" FROM ");
			sql.append(from.toString());

			if (!EmptyCondition.class.isInstance(where)) {
				sql.append(" WHERE ");
				sql.append(where.toString());
			}
		}

		if (!orderBy.isEmpty()) {
			sql.append(" ORDER BY");
			for (int i = 0; i < orderBy.size() - 1; i++) {
				sql.append(" ");
				sql.append(orderBy.get(i).toString());
				sql.append(",");
			}
			sql.append(" ");
			sql.append(orderBy.get(orderBy.size() - 1));
		}

		if (limit >= 0) {
			sql.append(" LIMIT ");
			sql.append(limit);
		}

		return sql.toString();
	}

	protected Object[] getParameters() {
		List<Object> parameters = new ArrayList<Object>();

		where.populateParameters(parameters);

		return parameters.toArray();
	}

	public Query select(Field... fields) {
		this.selectFields.addAll(Arrays.asList(fields));
		return this;
	}

	public Query from(TableName table) {
		this.from = new FromTable(table);
		return this;
	}

	public Query from(TableName table, String alias) {
		this.from = new FromTable(new TableNameWithAlias(table, alias));
		return this;
	}

	public Condition where(Field field) {
		return and(field);
	}

	public Condition where(Object value) {
		return and(value);
	}

	public Condition and(Field field) {
		where = where.and(field);
		return where;
	}

	public Condition and(Object value) {
		where = where.and(value);
		return where;
	}

	public Query orderBy(Field field) {
		this.orderBy.add(new FieldExpression(field));
		return this;
	}

	public Condition or(Field field) {
		where = where.or(field);
		return where;
	}

	public Join join(TableName table) {
		Join join = new Join(this, this.from, table);
		this.from = join;
		return join;
	}

	public Join join(TableName table, String alias) {
		return join(new TableNameWithAlias(table, alias));
	}

	public Join leftJoin(TableName table) {
		Join join = new LeftJoin(this, this.from, table);
		this.from = join;
		return join;
	}

	public Join leftJoin(TableName table, String alias) {
		return leftJoin(new TableNameWithAlias(table, alias));
	}

	public Query limit(int limit) {
		this.limit = limit;
		return this;
	}
}
