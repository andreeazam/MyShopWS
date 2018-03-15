
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/client")
public class WebServiceClient {
	
	@GET
	@Path("/loginclient")
	@Produces(MediaType.APPLICATION_JSON)
	public Client loginclient(
			@QueryParam("username") String userNameClient,
			@QueryParam("parola") String parolaClient) {

	  Client cl = new Client();
		
	  Client result = cl.existaClient(userNameClient, parolaClient);
	  return cl;

	}
	
	@GET
	@Path("/inregistrareclient")
	@Produces(MediaType.APPLICATION_JSON)
	public Client registerClient(@QueryParam("nume") String numeClient,
			              @QueryParam("prenume") String prenumeClient,
			              @QueryParam("username") String userNameClient,
			              @QueryParam("parola") String parolaClient,
			              @QueryParam("strada") String stradaClient,
			              @QueryParam("nrstrada") int numarStradaClient,
			              @QueryParam("telefon") long numarTelefonClient,
			              @QueryParam("email") String emailClient,
			              @QueryParam("lat") long latitudine,
			              @QueryParam("long") long longitudine) {
		 
		Client client = new Client();

		client.setNumeClient(numeClient);
		client.setPrenumeClient(prenumeClient);
		client.setParolaClient(parolaClient);
		client.setStradaClient(stradaClient);
		client.setNumarStradaClient(numarStradaClient);
		client.setNumarTelefonClient(numarTelefonClient);
		client.setEmailClient(emailClient);		
		client.setLongitudine(longitudine);
		client.setLatitudine(latitudine);
		
		client.inregistreazaClient(numeClient, prenumeClient, userNameClient, parolaClient, stradaClient, numarStradaClient, numarTelefonClient, emailClient, longitudine, latitudine);
		Client result = client.existaClient(userNameClient, parolaClient);
		return result;
	}	
}
