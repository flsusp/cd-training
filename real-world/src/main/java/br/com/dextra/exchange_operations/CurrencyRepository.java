package br.com.dextra.exchange_operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

		QueryResult result = store.select(Fields.CODE)
				.from(CURRENCY).where(Fields.CODE)
				.equalsTo(code).executeAndClose();

		Data data = result.unique();
		return new Currency(data.get(Fields.CODE)
				.getAsString());
	}

	public List<Currency> listAll() {
		List<Currency> currencies = new ArrayList<Currency>();
		SQLStore store = storeService.get();

		QueryResult result = store.select().from(CURRENCY)
				.executeAndClose();
		Collection<Data> currencyData = result.list();

		for (Data data : currencyData) {
			currencies.add(new Currency(data.get(
					Fields.CODE).getAsString()));
		}

		return currencies;
	}

}
