package br.com.dextra.exchange_operations;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.dextra.sqlstore.SQLStoreService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("currency")
public class CurrencyService {

	private final CurrencyRepository repository;

	public CurrencyService(SQLStoreService service) {
		this.repository = new CurrencyRepository(service);
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response listAll() {
		List<Currency> currencies = this.repository
				.listAll();
		Gson gson = new GsonBuilder().create();
		return Response.ok(gson.toJson(currencies)).build();
	}

}
