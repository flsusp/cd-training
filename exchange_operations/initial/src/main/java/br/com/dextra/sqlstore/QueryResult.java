package br.com.dextra.sqlstore;

import java.util.Collection;

public interface QueryResult {

	Data unique();

	Collection<Data> list();

	Value getGeneratedKeyFor(Field field);

	boolean isEmpty();

	QueryResultArray toArray(Field field);

}