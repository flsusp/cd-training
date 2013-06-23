package br.com.dextra.exchange_operations;

import static br.com.dextra.exchange_operations.OperationTableDefinition.OPERATION;
import static br.com.dextra.exchange_operations.OperationTableDefinition.OperationFields.CLIENT_DOCUMENT;
import static br.com.dextra.exchange_operations.OperationTableDefinition.OperationFields.CREATION_DATE;
import static br.com.dextra.exchange_operations.OperationTableDefinition.OperationFields.CURRENCY;
import static br.com.dextra.exchange_operations.OperationTableDefinition.OperationFields.ID;
import static br.com.dextra.exchange_operations.OperationTableDefinition.OperationFields.VALUE;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.SQLStoreService;

public class OperationRepository {

	private final SQLStoreService service;

	public OperationRepository(SQLStoreService service) {
		super();
		this.service = service;
	}

	public void save(ExchangeOperation operation) {
		SQLStore store = service.get();

		QueryResult result = store
				.create(OPERATION)
				.with(CLIENT_DOCUMENT,
						operation.getClientDocument())
				.with(CURRENCY,
						operation.getCurrency().getCode())
				.with(VALUE, operation.getValue())
				.with(CREATION_DATE,
						operation.getCreationDate())
				.executeAndClose();

		long id = result.getGeneratedKeyFor(ID)
				.requireAsLong();
		operation.setId(id);
	}
}
