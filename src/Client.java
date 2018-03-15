import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
	private long idClient;
	private String numeClient;
	private String prenumeClient;	
	private String userNameClient;
	private String parolaClient;	
	private  String stradaClient;
	private  int numarStradaClient;	
	private  long numarTelefonClient;
	private  String emailClient; 
	private  long latitudine;
	private  long longitudine;
	
	public  boolean existaClient;
	
	public long getIdClient() {
		return idClient;
	}
	
	public void setIdClient(long idClient){
			idClient = this.idClient;
	}
	
	public String getNumeClient() {
		return numeClient;
	}
	
	public void setNumeClient(String numeClient){
		numeClient = this.numeClient;
	}
	
	public String getPrenumeClient() {
		return prenumeClient;
	}
	
	public void setPrenumeClient(String prenumeClient){
		prenumeClient = this.prenumeClient;
	}
	
	public String getuserNameClient(){
		return userNameClient;
	}
	
	public void setUserNameClient(String userNameClient){
		this.userNameClient= userNameClient;
	}
	
	public String getParolaClient(){
		return parolaClient;
	}
	
	public void setParolaClient(String parolaClient){
		this.parolaClient= parolaClient;
	}
	
	public String getStradaClient() {
		return stradaClient;
	}
	
	public void setStradaClient(String stradaClient){
		stradaClient = this.stradaClient;
	}
	
	public int getNumarStradaClient() {
		return numarStradaClient;
	}
	
	public void setNumarStradaClient(int numarStradaClient){
		numarStradaClient = this.numarStradaClient;
	}
	
	public long getNumarTelefonClient(){
		return numarTelefonClient;
	}
	
	public void setNumarTelefonClient(long numarTelefonClient){
		numarTelefonClient = this.numarTelefonClient;
	}
	
	public String getEmailClient(){
		return emailClient;
	}
	
	public void setEmailClient(String emailClient){
		emailClient = this.emailClient;
	}
	
	public long getLongitudine(){
		return longitudine;
	}
	
	public void setLongitudine(long longitudine){
		longitudine = this.longitudine;
	}
	
	public long getLatitudine(){
		return latitudine;
	}
	
	public void setLatitudine(long latitudine){
		latitudine = this.latitudine;
	}
	
	public  Client existaClient(String userNameClient, String parolaClient){

		ResultSet rs;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIENTI WHERE USERNAME = ? AND PAROLA = ?");
		 	ps.setString(1, userNameClient); 
		 	ps.setString(2, parolaClient);
		 	rs = ps.executeQuery();
		 	
		 	if (!rs.next()){
		 		System.out.println("Nu s-a gasit clientul");
		 	}else{
		 		idClient           = rs.getLong(1);
		 		numeClient         = rs.getString ( 2);
		 		prenumeClient      = rs.getString ( 3);
		 		numarTelefonClient = rs.getLong   ( 4);
		 		emailClient        = rs.getString ( 5);
		 		this.userNameClient     = rs.getString ( 6);	
		 		this.parolaClient = rs.getString(7);
		 		stradaClient       = rs.getString ( 8);
		 		numarStradaClient  = rs.getInt    ( 9);
		 		longitudine        = rs.getLong   (10);
		 		latitudine         = rs.getLong   (11);
		 		existaClient       = true;
		 	}
		 			
		}catch(SQLException e){
			e.getMessage();
		}
		 
		System.out.println(String.valueOf(existaClient));
		return this;
	}
	
	public Client inregistreazaClient(String numeClient, String prenumeClient, String userNameClient, String parolaClient, String stradaClient, int numarStradaClient, long numarTelefonClient, String emailClient, long longitudine, long latitudine){
		PreparedStatement ps = null;
		boolean existaClient = false;
		int res = 0;
		try{
			 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
			 ps = conn.prepareStatement("INSERT INTO CLIENTI(NUME, PRENUME, TELEFON, EMAIL, USERNAME, PAROLA, STRADADEFAULT, NUMARSTRADADEFAULT, LONGITUDINE, LATITUDINE) "
					 					+ "VALUES (?,?,?,?,?,?,?,?,?,?) ");					 									
			 	ps.setString(1, numeClient);
			 	ps.setString(2, prenumeClient);
			 	ps.setLong(3, numarTelefonClient);
			 	ps.setString(4, emailClient);
			 	ps.setString(5, userNameClient);
			 	ps.setString(6, parolaClient);
			 	ps.setString(7, stradaClient);
			 	ps.setInt(8, numarStradaClient);
			 	ps.setLong(9, longitudine);
			 	ps.setLong(10, latitudine);
			 				 	
			 	res = ps.executeUpdate();
			 	existaClient = true;
			 				
		}catch(SQLException e){
			e.printStackTrace();
		}		
		return this;
	}
	
	public  Client loadClientDetails(String userNameClient, String parolaClient){
		ResultSet rs;
		Client cl = null;
		
		try{
		 Connection conn = DriverManager.getConnection(DBConnect.getURL(),DBConnect.getUSERNAME(),DBConnect.getPASSWORD());
		 PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIENTI WHERE USERNAME LIKE ? AND PAROLA LIKE ?");
		 	ps.setString(1, userNameClient); 
		 	ps.setString(2, parolaClient);
		 	rs = ps.executeQuery();
		 	
		 	if (!rs.next()){
		 		System.out.println("Date gresite!");
		 	}else{
		 		existaClient = true;
		 		cl = new Client();
		 		idClient = rs.getLong(1);
		 		numeClient = rs.getString(2);
		 		cl.setNumeClient(numeClient);
		 		prenumeClient = rs.getString(3);
		 		numarTelefonClient = rs.getLong(4);
		 		emailClient=rs.getString(5);
		 		stradaClient=rs.getString(8);
		 		numarStradaClient= rs.getInt(9);
		 		longitudine = rs.getLong(10);
		 		latitudine = rs.getLong(11);
		 	}
		 			
		}catch(SQLException e){
			e.getMessage();
		}
		 
		System.out.println(String.valueOf(existaClient));
		
		return cl;		
	}
}
	
	