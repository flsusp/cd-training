package br.com.dextra.sqlstore;


public enum Operator {

	NotEquals("!=", false), Equals("=", false), And("AND", true), Or("OR", true), IsNull(null, false),
	GreaterThanOrEquals(">=", false), Between("BETWEEN", false), In("IN", false), LessThanOrEquals("<=", false);

	private String stringRepresentation;
	private boolean useParenthesis;

	private Operator(String stringRepresentation, boolean useParenthesis) {
		this.stringRepresentation = stringRepresentation;
		this.useParenthesis = useParenthesis;
	}

	public String toString() {
		return stringRepresentation;
	}

	public boolean useParenthesis() {
		return useParenthesis;
	}
}
