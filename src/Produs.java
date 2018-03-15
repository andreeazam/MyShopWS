import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Produs {
    private long idProdus;
    private String denumireProdus;
    private double pretProdus;
    private int cantitateInStoc;

    public boolean existaProdus = false;
    public boolean existaInStoc = false;

    public Produs(long idProdus, String denumireProdus, double pretProdus, int cantitateInStoc){
        this.idProdus = idProdus;
        this.denumireProdus = denumireProdus;
        this.pretProdus = pretProdus;
        this.cantitateInStoc = cantitateInStoc;
    }

    public Produs(){
        
    }
    
	public long getIdProdus() {
		return idProdus;
	}
	
	public void setIdProdus(long idProdus){
		this.idProdus = idProdus;
	}
	
	public String getDenumireProdus(){
		return denumireProdus;
	}
	
	public double getPretProdus(){
		return pretProdus;
	}
	
	public int getCantitateInStoc(){
		return cantitateInStoc;
	}
    
	public void setCantitateInStoc(int cantitateInStoc){
		cantitateInStoc = this.cantitateInStoc;
	}
	public boolean getExistaInStoc(){
		return existaInStoc;
	}
    
	public void setExistaInStoc(boolean existaInStoc){
		existaInStoc = this.existaInStoc;
	}
	
	public Produs incarcaDetaliiProdus(long idProdus){
 		Produs pr = new Produs();

		ResultSet rs;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRODUSE WHERE ID_PRODUS = ?");
		 System.out.println("Query executes");
		 	ps.setLong(1, idProdus); 
		 	
		 	rs = ps.executeQuery();
		 	
		 	if (!rs.next()){
		 		System.out.println("Nu exista produsul cerut");
		 	
		 	}else{

		 		pr.idProdus         = idProdus;
		 		pr.denumireProdus   = rs.getString( 2);
		 		pr.pretProdus       = rs.getLong  ( 3);
		 		pr.cantitateInStoc  = rs.getInt   ( 4);
		 		System.out.println(pr.cantitateInStoc);
		 		pr.existaProdus       = true;
		 		if (pr.cantitateInStoc<=0){
		 			pr.existaInStoc = false;
		 		}else{
		 			pr.existaInStoc = true;	 			
		 		}
		 	}
		 			
		}catch(SQLException e){
			e.getMessage();
		}
			 
		System.out.println(String.valueOf(pr.existaProdus));
		
		return pr;
	}
}
