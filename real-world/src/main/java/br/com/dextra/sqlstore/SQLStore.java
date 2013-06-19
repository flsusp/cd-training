package br.com.dextra.sqlstore;


public interface SQLStore {

	SQLQuery sql(String sql);

	SQLQuery sql(StringBuilder sql);

	void close();

	Query select(Field... fields);

	Insert create(TableName table);

	void commit();

	void rollback();

	void beginTransaction();

	Update update(TableName table);

	Delete deleteFrom(TableName table);
}