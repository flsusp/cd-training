package br.com.dextra.sqlstore;

public interface Data {

	boolean has(Field field);

	Value get(Field field);

	Value getOptional(Field field);
}