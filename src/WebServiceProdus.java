
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/produs")
public class WebServiceProdus {
	@GET
	@Path("/loadprodus")
	@Produces(MediaType.APPLICATION_JSON)

	public Produs loadProdus(
			@QueryParam("idProdus") long idProdus) {	
	  
		Produs pr = new Produs();
		Produs result = pr.incarcaDetaliiProdus(idProdus);
	  return result;
	}
}