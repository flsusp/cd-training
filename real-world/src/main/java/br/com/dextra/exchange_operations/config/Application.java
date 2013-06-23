package br.com.dextra.exchange_operations.config;

import java.util.HashSet;
import java.util.Set;

import br.com.dextra.exchange_operations.CurrencyService;
import br.com.dextra.exchange_operations.OperationService;
import br.com.dextra.sqlstore.SQLStoreService;
import br.com.dextra.sqlstore.impl.BoneCPJDBCStoreService;

public class Application extends
		javax.ws.rs.core.Application {

	private final SQLStoreService storeService;

	public Application() {
		super();
		this.storeService = new BoneCPJDBCStoreService();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>();
	}

	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> singletons = new HashSet<Object>();
		singletons.add(new OperationService(storeService));
		singletons.add(new CurrencyService(storeService));
		return singletons;
	}
}
