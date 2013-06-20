package br.com.dextra.sqlstore;

public abstract class Alias {

	public static Field field(Field field) {
		return field;
	}

	public static Field field(String field) {
		return new Name(field);
	}

	public static Field field(String alias, Field field) {
		return new AliasField(alias, field);
	}

	public static Field field(TableName tableName, Field field) {
		return new AliasField(tableName.name(), field);
	}

	public static Field all(String alias) {
		return new AliasAll(alias);
	}
}
