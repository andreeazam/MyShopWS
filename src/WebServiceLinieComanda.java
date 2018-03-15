
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/liniecomanda")
public class WebServiceLinieComanda {
	@GET
	@Path("/creareliniecomanda")
	@Produces(MediaType.APPLICATION_JSON)
	public LinieComanda registerLinieComanda(@QueryParam("idComandaClient") long idComandaClient,
			              @QueryParam("idProdus") long idProdus,
			              @QueryParam("cantitate") int cantitate,
			              @QueryParam("pretProdus") double pretProdus,
			              @QueryParam("lccValoare") double lccValoare) {
		 
		LinieComanda linieComanda = new LinieComanda();
		lccValoare = cantitate*pretProdus;
		
		linieComanda.setIdComandaClient(idComandaClient);
		linieComanda.setIdProdus(idProdus);
		linieComanda.setCantitate(cantitate);
		linieComanda.setPretProdus(pretProdus);
		linieComanda.setLccValoare(lccValoare);
		
		linieComanda.inregistreazaLinieComanda(idComandaClient, idProdus, cantitate, pretProdus, lccValoare);
		linieComanda.updateValoareComandaClient(idComandaClient, lccValoare);
		
		System.out.println("Before call");
		System.out.println(linieComanda.valoareVecheComanda(idComandaClient)+ "valoare veche comanda amount");
		System.out.println("idProdus: "+idProdus);
		System.out.println("cantitate: "+cantitate);
		System.out.println("pretProdus: "+pretProdus);
		System.out.println("lccValoare: "+lccValoare);
		
		return linieComanda.loadDetaliiLinie(idComandaClient);
	//return linieComanda;
	}
	
	@GET
	@Path("/loadliniicomanda")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LinieComanda> obtineLiniiComenziClient(@QueryParam("idComandaClient") long idComandaClient) {	
		LinieComanda linieComanda = new LinieComanda();
	    List<LinieComanda> result = linieComanda.obtineLiniiComandaClient(idComandaClient);
		return result;
	}
}
  