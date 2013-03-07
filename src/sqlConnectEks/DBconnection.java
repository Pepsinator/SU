package pakke;

import java.sql.Connection;
import java.util.Properties;

public class DBconnection {
	
	private String jdbcDriver;
	private Connection conn;
	private String url;
	private Properties properties;
	//private string sql;
	
	public DBConnection(Properties properties){
		jdbcDriver=properties.getProperty("jbcDriver";
		url=properties.getProperty("url");
	}
	
	public void initialize() throws SQLException, Class NotFoundException{
		
		Class.forName(jdbcDriver) ;
		
		Properties info=new Properties();
		info.setProperty("user", "mysql2");
		info.setProperty("password", "mysql2");
		conn=DriveManager.getConnection(url, info);
		
	}
	
	public ResultSet makeSingleQuery() throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;
	}
	
	public void makeUpdate(String sql) throws SQLException{
		Statement st = conn.createStatement();
		return st.executeUpdate(sql);
	}
	
	public void makeBatchUpdate(String sql){
		PreparedStatement st = conn.prepareStatement();
		return st;
	}
	
	public void close() throws SQLException{
		conn.close();
	}
	
	public static void main(String args[]){
		
		Properties p = new Properties();
		p.load(new FileInputStream(new File("Properties.properties")));
		DBConnection db = new DBConnection();
		db.initialize();
		
	}

}
