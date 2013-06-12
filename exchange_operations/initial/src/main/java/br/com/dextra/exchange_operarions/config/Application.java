package br.com.dextra.exchange_operarions.config;

import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>();
	}

	@Override
	public Set<Object> getSingletons() {
		return new HashSet<Object>();
	}
}
