import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnect {
	/*public static String URL = "jdbc:oracle:thin:@37.120.250.20:1521:oracle";
	public static String USERNAME = "ZAMFIR_ANDREEA";
	public static String PASSWORD = "stud";
	*/
	public static String URL = "jdbc:oracle:thin:@localhost:1521:andreeaz";
	public static String USERNAME = "SCOTT";
	public static String PASSWORD = "stud";
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null ;	
	
	public static String getURL() {
		return URL;
	}
	
	public static String getUSERNAME() {
		return USERNAME;
	}
	
	public static String getPASSWORD() {
		return PASSWORD;
	}
}
