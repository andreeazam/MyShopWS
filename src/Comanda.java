import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comanda {

	public long idComandaClient;
	public long numarComanda;
	public long idClient;
	public double valoareComanda;
	public int idStatus;
	
	public String textStatus = "-";
		
	public long getIdComandaClient(){
		return idComandaClient;
	}
	
	public void setIdComandaClient(long idComandaClient){
		this.idComandaClient = idComandaClient;
	}
	
	public long getNumarComanda(){
		return numarComanda;
	}
	
	public void setNumarComanda(long numarComanda){
		this.numarComanda = numarComanda;
	}
	
	public long getIdClient() {
		return idClient;
	}
	
	public void setIdClient(long idClient){
		this.idClient = idClient;
	}
	
	public double getValoareComanda(){
		return valoareComanda;
	}
	
	public void setValoareComanda(double valoareComanda){
		this.valoareComanda = valoareComanda;
	}
	
	public int getIdStatus(){
		return idStatus;
	}
	
	public void setIdStatus(int idStatus){
		this.idStatus = idStatus;
	}
	
	public String getTextStatus(){
		return textStatus;
	}
	
	public void setTextStatus(String textStatus){
		this.textStatus = textStatus;
	}
	
	public Comanda(){
		
	}
	
	public Comanda inregistreazaComanda(long idClient){
		PreparedStatement ps = null;
		boolean existaComandaInCurs = false;
		int res = 0;
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 ps = conn.prepareStatement("INSERT INTO COMENZICLIENTI(ID_CLIENT,ID_STATUS, VALOARE) "
					 					+ "VALUES (?,?,?) ");					 									
			 	ps.setLong(1, idClient);
			 	ps.setInt(2, 1);
			 	ps.setInt(3, 0);
			 				 	
			 	res = ps.executeUpdate();
			 	existaComandaInCurs = true;
			 	System.out.println("Avem noua comanda "+ idClient+" "+ existaComandaInCurs);
			 				
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return this;
	}
	
	public  Comanda loadDetaliiComandaNoua(long idClient){
		ResultSet rs;
		Comanda com  = null;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM COMENZICLIENTI WHERE ID_CLIENT = ? AND ID_STATUS = 1");
		 	ps.setLong(1, idClient); 
		 	rs = ps.executeQuery();
		 	
			 com = new Comanda();
		 	
		 	if (!rs.next()){
		 		System.out.println("Eroare!");
		 	}else{
		 	
		 		com.idComandaClient = rs.getLong("ID_COMANDACLIENT");
		 		com.numarComanda = rs.getLong("NUMARCOMANDA");
		 		com.idClient = rs.getLong("ID_CLIENT");
		 		com.valoareComanda = rs.getInt("VALOARE");
		 		com.idStatus = rs.getInt("ID_STATUS");	 		
		 	}
		 			
		}catch(SQLException e){
			e.getMessage();
		}
		
		return com ;		
	}
	
	public  boolean existaComandaInCurs(long idClient){
		ResultSet rs;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM COMENZICLIENTI WHERE ID_CLIENT = ? AND ID_STATUS = 1");
		 	ps.setLong(1, idClient); 
		 	rs = ps.executeQuery();
		 	
		 	return rs.next();
		}catch(SQLException e){
			e.getMessage();		
		}
		return false;
	}
	
	public Comanda trimitereComanda(long idComandaClient){
		PreparedStatement pst;
		int rs;
		Comanda com  = null;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		    pst = conn.prepareStatement("update COMENZICLIENTI set id_status = 2 where ID_COMANDACLIENT = ?");
		 	pst.setLong(1, idComandaClient); 
		 	rs = pst.executeUpdate();
		 	
		 	pst.close();
		 	conn.close();
			 com = new Comanda();
		 			
		}catch(SQLException e){
			e.getMessage();
		}
		
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			    pst = conn.prepareStatement("select * from comenziclienti where ID_COMANDACLIENT = ?");
			 	pst.setLong(1, idComandaClient); 
			 	ResultSet rst = pst.executeQuery();
			 	
			 	if(!rst.next()){
			 		System.out.println("Nu Exista");
			 	}else{
			 		com = new Comanda();
			 		com.idComandaClient = rst.getLong(1);
			 		com.numarComanda = rst.getLong(2);
			 		com.idClient = rst.getLong(3);
			 		com.valoareComanda = rst.getDouble(4);
			 		com.idStatus = rst.getInt(5);
			 		
			 	}			 		
			 	pst.close();
			 	conn.close();
			}catch(SQLException e){
				e.getMessage();
			}	
		return com;
	}
	
	public List<Comanda> obtineComenziClient(long idClient){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Comanda> comList = new ArrayList<>();
		try {
		Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		ps = conn.prepareStatement("select cc.numarcomanda, cc.valoare, s.DESCRIERESTATUS, cc.id_comandaclient from comenziclienti cc, status s where cc.id_client = ? and s.ID_STATUS = cc.ID_STATUS order by cc.id_status");
		ps.setLong(1, idClient);
		
		rs = ps.executeQuery();
		
		while (rs.next()) {
			 Comanda com = new Comanda();
			 int index = 1;
			 com.setNumarComanda(rs.getLong(index++));
			 com.setValoareComanda(rs.getDouble(index++));
			 com.setTextStatus(rs.getString(index++));
			 com.setIdComandaClient(rs.getLong(index++));
			 com.setIdClient(idClient);
			 
			 comList.add(com);
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
		return comList;
	}
	
	public Comanda incarcaDetaliiComanda(long idClient){
		
		PreparedStatement ps;
		ResultSet rs = null;
		Comanda com = new Comanda();
		try{
		Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		ps = conn.prepareStatement("select * from (Select * from comenziclienti c where c.id_client = ? and c.id_status < 5 order by c.id_status desc) where rownum <= 1");
		ps.setLong(1, idClient);
		
		rs = ps.executeQuery();
		
		
		if(rs.next()){
			
			 com.idComandaClient = rs.getLong("ID_COMANDACLIENT");
			 com.numarComanda = rs.getLong("NUMARCOMANDA");
			 com.idClient=rs.getLong("ID_CLIENT");
			 com.valoareComanda=rs.getDouble("VALOARE");
			 com.idStatus = rs.getInt("ID_STATUS");
		}
		
        ps.close();
        conn.close();
        
		}catch(SQLException e){
			e.getMessage();
		}
		return com;
	}
	
	public Comanda confirmaPrimire(long idComanda){
		
		PreparedStatement ps = null;
		ResultSet rs;
		Comanda com = new Comanda();
		
		try{
			Connection conn =  DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			ps = conn.prepareStatement("update COMENZICLIENTI set id_status = 5 where ID_COMANDACLIENT = ? and ID_STATUS = 4");
			ps.setLong(1, idComanda);
			
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		try{
			Connection conn =  DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			ps = conn.prepareStatement("select * from comenziclienti where id_comandaclient = ?");
			ps.setLong(1, idComanda);
			
			rs = ps.executeQuery();
			
	
			if(rs.next()){
				com.setIdComandaClient(rs.getLong(1));
				com.setNumarComanda(rs.getLong(2));
				com.setIdClient(rs.getLong(3));
				com.setValoareComanda(rs.getDouble(4));
				com.setIdStatus(rs.getInt(5));
				
				}else{
					System.out.println("Nu s-a gasit comanda!");
				}
			
			ps.close();
			conn.close();	
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return com;
	}
}