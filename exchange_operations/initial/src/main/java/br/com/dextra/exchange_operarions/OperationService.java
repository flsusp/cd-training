package br.com.dextra.exchange_operarions;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("operation")
public class OperationService {

	private final OperationRepository operationRepository = new OperationRepository();

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response createOperation(@FormParam("value") Double value, @FormParam("currency") Currency currency,
			@FormParam("clientDocument") String clientDocument) {

		ExchangeOperation operation = new ExchangeOperation(clientDocument);
		operation.setValue(value);
		operation.setCurrency(currency);

		operation.exchange();

		operationRepository.save(operation);

		return Response.ok().build();
	}
}
