package br.com.dextra.sqlstore;


import java.util.List;

public abstract class Expression {

	public abstract String toString();

	protected abstract void populateParameters(List<Object> parameters);
}
