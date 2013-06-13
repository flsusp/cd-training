package br.com.dextra.exchange_operarions;

import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.Name;
import br.com.dextra.sqlstore.TableName;

public class OperationTableDefinition {

	public static final TableName OPERATION = new Name("OPERATION");

	public static enum OperationFields implements Field {
		ID, CLIENT_DOCUMENT, CURRENCY, VALUE, CREATION_DATE;
	}
}
