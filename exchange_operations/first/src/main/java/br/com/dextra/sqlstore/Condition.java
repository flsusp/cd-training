package br.com.dextra.sqlstore;


public abstract class Condition extends Expression {

	protected final Query query;

	public Condition(Query query) {
		super();
		this.query = query;
	}

	public abstract String toString();

	public abstract Query equalsTo(Expression expression);

	public abstract Query equalsTo(Field field);

	public abstract Query equalsTo(Object value);

	public abstract Query notEqualsTo(Expression expression);

	public abstract Query notEqualsTo(Field field);

	public abstract Query notEqualsTo(Object value);

	public abstract Condition and(Field field);

	public abstract Condition or(Field field);

	public abstract Query isNull();

	public abstract Query greaterThanOrEqualsTo(Expression expression);

	public abstract Query greaterThanOrEqualsTo(Field field);

	public abstract Query greaterThanOrEqualsTo(Object value);

	public abstract Query lessThanOrEqualsTo(Expression expression);

	public abstract Query lessThanOrEqualsTo(Field field);

	public abstract Query lessThanOrEqualsTo(Object value);

	public abstract Condition and(Object value);

	public abstract Query between(Expression expressionStart, Expression expressionEnd);

	public abstract Query between(Field fieldStart, Field fieldEnd);

	public abstract Query between(Field fieldStart, Object valueEnd);

	public abstract Query between(Object valueStart, Object valueEnd);

	public abstract Query between(Object valueStart, Field fieldEnd);

	public abstract Query in(Object... values);

	public abstract Query in(Long... values);
}
