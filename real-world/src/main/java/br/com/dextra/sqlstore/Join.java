package br.com.dextra.sqlstore;


public class Join implements From {

	protected final From leftSide;
	protected final FromTable rightSide;
	protected Condition on;

	public Join(Query query, From leftSide, TableName rightSideTable) {
		this.leftSide = leftSide;
		this.rightSide = new FromTable(rightSideTable);
		this.on = new EmptyCondition(query);
	}

	public Condition on(Field field) {
		this.on = this.on.and(field);
		return this.on;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(leftSide.toString());
		s.append(" JOIN ");
		s.append(rightSide.toString());
		s.append(" ON (");
		s.append(on.toString());
		s.append(")");
		return s.toString();
	}
}
