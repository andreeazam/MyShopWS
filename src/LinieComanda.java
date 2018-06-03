import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class LinieComanda {
	private long idLinieComanda;
	private long idComandaClient;
	private long idProdus;
	private int cantitate;
	private double pretProdus;
	private double lccValoare;
	
	private String denumireProdus = "-";
	
	//Pentru update comanda:
	double valoareNoua;
	double valoareVeche;
	
	public long getIdLinieComanda(){
		return idLinieComanda;
	}
	
	public void setIdLinieComanda(long idLinieComanda){
		this.idLinieComanda= idLinieComanda;
	}
	
	public long getIdComandaClient(){
		return idComandaClient;
	}
	
	public void setIdComandaClient(long idComandaClient){
		this.idComandaClient = idComandaClient;
		
	}
	
	public long getIdProdus(){
		return idProdus;
	}
	
	public void setIdProdus(long idProdus){
		this.idProdus = idProdus;
	} 
	
	public int getCantitate(){
		return cantitate;
	}
	
	public void setCantitate(int cantitate){
		this.cantitate = cantitate;
	}
	
	public double getPretProdus(){
		return pretProdus;
	}
	
	public void setPretProdus(double pretProdus){
		this.pretProdus = pretProdus;
	}
	
	public double getLccValoare(){
		return lccValoare; 
	}
	
	public void setLccValoare(double lccValoare){
		this.lccValoare = lccValoare;
	}
	
    public String getDenumireProdus(){
        return denumireProdus;
    }
    
    public void setDenumireProdus(String denumireProdus){
        this.denumireProdus = denumireProdus;
    }
	
	public LinieComanda(){
		
	}
	
	
	
	public LinieComanda inregistreazaLinieComanda(long idComanda, long idProdus, int cantitate, double pretProdus, double lccValoare){
		PreparedStatement ps = null;
	
		int res = 0;
		lccValoare = cantitate*pretProdus;
		//Vefifica daca exista jeja linia pentru produs
		try{		
			 ResultSet rsc;
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 
			 ps = conn.prepareStatement("SELECT * FROM LINIICOMANDACLIENTI WHERE ID_COMANDACLIENT = ? AND ID_PRODUS = ?");
			 System.out.println("Se verifica daca  exista linia deja");
			 ps.setLong(1, idComanda);
			 ps.setLong(2, idProdus);
			 
			 rsc = ps.executeQuery();
			 if(!rsc.next()){
			
			// System.out.println("Se creeaza linia noua cu atributele"+idComanda+idProdus+cantitate+pretProdus+lccValoare);				 
			 			ps = conn.prepareStatement("INSERT INTO LINIICOMANDACLIENTI(ID_COMANDACLIENT,ID_PRODUS, CANTITATE, PRETPRODUS, LCCVALOARE) "
					 					+ "VALUES (?,?,?,?,?) ");			
			 			System.out.println("S-a apelat ps de linie existenta. ");
			 			ps.setLong(1, idComanda);
			 			ps.setLong(2, idProdus);
			 			ps.setInt(3, cantitate);
			 			ps.setDouble(4, pretProdus);
			 			ps.setDouble(5, lccValoare);
			 				 				 	
			 		    ps.executeUpdate();
			 			conn.close();			 	
			 }else{
				 
				 long idLinieComanda = rsc.getLong(1);
				 int cantitateNoua = cantitate + rsc.getInt(4);
				 double valoareNoua = lccValoare + rsc.getInt(6);
     			    ps = conn.prepareStatement(" UPDATE LINIICOMANDACLIENTI l SET l.CANTITATE = ?, l.LCCVALOARE = ? WHERE l.LINIECOMANDACLIENT_ID = ?");
				 	ps.setInt(1, cantitateNoua);
				 	ps.setDouble(2, valoareNoua);
				 	ps.setLong(3, idLinieComanda);
				 	
				 	ps.executeUpdate();
				 	ps.close();
				 	conn.close();
			 }	
	
	
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return this;
	}
	
	
	
	public double valoareVecheComanda(long idComandaClient){
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 ps = conn.prepareStatement("select valoare from comenziclienti where id_comandaclient = ?");					 									
			 	ps.setLong(1, idComandaClient);
			 		System.out.println(idComandaClient+" id-ul comanda client de la android");
			 	
			 	rs = ps.executeQuery();
			 	if(!rs.next()){
			 		System.out.println("Eroare valoare veche");
			 	
			 	}else{
			 		this.valoareVeche = rs.getDouble(1);
			 	}
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return valoareVeche;		
	}
	
	public double updateValoareComandaClient(long idComandaClient, double lccValoare){
		PreparedStatement ps = null;
		int res = 0;
		Comanda com = new Comanda();
		double valoareVeche ;
		
		double x = valoareVecheComanda(idComandaClient);
		
		System.out.println("Valoarea veche a comenzii este "+ this.valoareVeche);
		valoareNoua = x + lccValoare;
		System.out.println("Valoarea noua a comenzii este "+ valoareNoua);
		
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 ps = conn.prepareStatement("update COMENZICLIENTI set valoare = ? where ID_COMANDACLIENT = ?");					 									
			 	ps.setDouble(1, valoareNoua);
			 	ps.setLong(2, idComandaClient);
			 				 	
			 	res = ps.executeUpdate();
			 				
		}catch(SQLException e){
			e.printStackTrace();
		}
		return valoareNoua;
	}
	
	public LinieComanda loadDetaliiLinie(long idComandaClient){
		PreparedStatement ps = null;
		ResultSet rs = null;
	  
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 ps = conn.prepareStatement("select * from (select l.LINIECOMANDACLIENT_ID, l.ID_COMANDACLIENT, l.ID_PRODUS, l.CANTITATE, l.PRETPRODUS, l.LCCVALOARE from LINIICOMANDACLIENTI l, clienti c, COMENZICLIENTI cc  where l.ID_COMANDACLIENT = cc.ID_COMANDACLIENT and c.id_client = cc.ID_CLIENT and cc.ID_COMANDACLIENT = ? order by l.liniecomandaclient_id desc) where rownum <= 1");					 									
			 	ps.setLong(1, idComandaClient);
			 	
			 	rs = ps.executeQuery();
			 	if(!rs.next()){
			 		System.out.println("Eroare valoare veche");
			 	
			 	}else{
				 	LinieComanda linieComanda = new LinieComanda();
				 	
				 	idLinieComanda = rs.getLong(1);
				 	this.idComandaClient = rs.getLong(2);
				 	idProdus = rs.getLong(3);
				 	cantitate = rs.getInt(4);
				 	pretProdus = rs.getDouble(5);
				 	lccValoare = rs.getDouble(6);			 	
			 	}
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return this;
	}
	

	public ArrayList<LinieComanda> obtineLiniiComandaClient(long idComandaClient){
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LinieComanda> lcomList = new ArrayList<LinieComanda>();
		try {
		Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		ps = conn.prepareStatement("select l.LINIECOMANDACLIENT_ID, l.ID_COMANDACLIENT,l.id_PRODUS, l.CANTITATE,l.PRETPRODUS,l.LCCVALOARE, p.DENUMIREPRODUS from LINIICOMANDACLIENTI l, PRODUSE p where l.ID_PRODUS = p.ID_PRODUS and l.ID_COMANDACLIENT = ?");
		ps.setLong(1, idComandaClient);
		
		//LinieComanda lcom = new LinieComanda();
		
		rs = ps.executeQuery();
		//LinieComanda lcom = new LinieComanda();
		while (rs.next()) {
			 LinieComanda lcom = new LinieComanda();
			 int index = 1;
			
				lcom.setIdLinieComanda(rs.getLong(index++));
				lcom.setIdComandaClient(rs.getLong(index++));
				lcom.setIdProdus(rs.getLong(index++));
		        lcom.setCantitate(rs.getInt(index++));
				lcom.setPretProdus(rs.getDouble(index++));
				lcom.setLccValoare(rs.getLong(index++));
				lcom.setDenumireProdus(rs.getString(index++));
				
				lcomList.add(lcom);
		}
		
		}catch(SQLException e){
			e.getMessage();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException sqle) {
				System.out.println("An exception has occurred: " + sqle.getMessage());
			}
		}
		return lcomList;
	}
	
}
