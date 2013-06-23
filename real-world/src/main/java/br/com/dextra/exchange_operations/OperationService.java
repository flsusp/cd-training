package br.com.dextra.exchange_operations;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.dextra.exchange_operations.cash_register.CashRegister;
import br.com.dextra.exchange_operations.cash_register.JustLogCashRegister;
import br.com.dextra.exchange_operations.currency_converter.CurrencyConverter;
import br.com.dextra.exchange_operations.currency_converter.MockedCurrencyConverter;
import br.com.dextra.sqlstore.SQLStoreService;

@Path("operation")
public class OperationService {

	private final OperationRepository operationRepository;
	private final CashRegister cashRegister = new JustLogCashRegister();
	private final CurrencyConverter currencyConverter = new MockedCurrencyConverter();

	public OperationService(SQLStoreService service) {
		super();
		this.operationRepository = new OperationRepository(service);
	}

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response createOperation(@FormParam("value") Double value,
			@FormParam("currency") CurrencyCode currency,
			@FormParam("clientDocument") String clientDocument) {

		ExchangeOperation operation = new ExchangeOperation(clientDocument);
		operation.setValue(value);
		operation.setCurrency(currency);

		operation.exchange(cashRegister, currencyConverter);

		operationRepository.save(operation);

		return Response.ok().build();
	}
}
