package br.com.dextra.exchange_operations;

import br.com.dextra.sqlstore.Data;
import br.com.dextra.sqlstore.Field;
import br.com.dextra.sqlstore.Name;
import br.com.dextra.sqlstore.QueryResult;
import br.com.dextra.sqlstore.SQLStore;
import br.com.dextra.sqlstore.SQLStoreService;
import br.com.dextra.sqlstore.TableName;

public class CurrencyRepository {

	private static final TableName CURRENCY = new Name(
			"CURRENCY");
	private final SQLStoreService storeService;

	private static enum Fields implements Field {
		CODE;
	}

	public CurrencyRepository(SQLStoreService store) {
		super();
		this.storeService = store;
	}

	public Currency findByCode(String code) {
		SQLStore store = storeService.get();

		QueryResult result = store.select(Fields.CODE).from(CURRENCY)
				.where(Fields.CODE).equalsTo(code)
				.executeAndClose();

		Data data = result.unique();
		return new Currency(data.get(Fields.CODE).getAsString());
	}
}
