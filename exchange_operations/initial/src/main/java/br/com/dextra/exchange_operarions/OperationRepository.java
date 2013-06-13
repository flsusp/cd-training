package br.com.dextra.exchange_operarions;

import static br.com.dextra.exchange_operarions.OperationTableDefinition.OPERATION;
import static br.com.dextra.exchange_operarions.OperationTableDefinition.OperationFields.CLIENT_DOCUMENT;
import static br.com.dextra.exchange_operarions.OperationTableDefinition.OperationFields.CREATION_DATE;
import static br.com.dextra.exchange_operarions.OperationTableDefinition.OperationFields.CURRENCY;
import static br.com.dextra.exchange_operarions.OperationTableDefinition.OperationFields.ID;
import static br.com.dextra.exchange_operarions.OperationTableDefinition.OperationFields.VALUE;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.SQLStoreService;
import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;

public class OperationRepository {

	private SQLStoreService service = new BoneCPJDBCStoreService();

	public void save(ExchangeOperation operation) {
		SQLStore store = service.get();

		QueryResult result = store.create(OPERATION).with(CLIENT_DOCUMENT, operation.getClientDocument())
				.with(CURRENCY, operation.getCurrency()).with(VALUE, operation.getValue())
				.with(CREATION_DATE, operation.getCreationDate()).executeAndClose();

		long id = result.getGeneratedKeyFor(ID).requireAsLong();
		operation.setId(id);
	}
}
