
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/comanda")
public class WebServiceComanda {
	@GET
	@Path("/inregistrarecomanda")
	@Produces(MediaType.APPLICATION_JSON)
	public Comanda registerComanda(@QueryParam("idClient") long idClient,			
								@QueryParam("idStatus") int idStatus)
							{
		 
		Comanda comanda = new Comanda();

		comanda.setIdClient(idClient);
		comanda.setValoareComanda(0);
		comanda.setIdStatus(idStatus);		
		
		if(!comanda.existaComandaInCurs(idClient)){
		comanda.inregistreazaComanda(idClient);
		}else{
			System.out.println("Exista comanda in curs");
		}
		Comanda result = comanda.loadDetaliiComandaNoua(idClient);
		return result;
	}	
	
	@GET
	@Path("/trimiterecomanda")
	@Produces(MediaType.APPLICATION_JSON)
	public Comanda trimitereComanda(@QueryParam("idComandaClient") long idComandaClient)							
							{		 
		
		Comanda comanda = new Comanda();
	    Comanda result = comanda.trimitereComanda(idComandaClient);	;
		return result;
	}	
	
	@GET
	@Path("/incarcadetaliicomanda")
	@Produces(MediaType.APPLICATION_JSON)
	public Comanda incarcaDetaliiComanda(@QueryParam("idClient") long idClient)							
							{		 
		
		Comanda comanda = new Comanda();
	    Comanda result = comanda.incarcaDetaliiComanda(idClient);	;
		return result;
	}
	
	@GET
	@Path("/obtinecomenziclient")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comanda> obtineComenziClient(@QueryParam("idClient") long idClient) {	
		Comanda comanda = new Comanda();
	    List<Comanda> result = comanda.obtineComenziClient(idClient);
		return result;
	}
	
	@GET
	@Path("/confirmaprimire")
	@Produces(MediaType.APPLICATION_JSON)
	public Comanda confirmaPrimire(@QueryParam("idComandaClient") long idComanda)							
							{		 	
		Comanda comanda = new Comanda();
		comanda.setIdComandaClient(idComanda);
	    Comanda result = comanda.confirmaPrimire(idComanda);	
	    Produs produs = new Produs();
	    produs.updateCantitateStoc(comanda);
		return result;
	}
}
