package pakke;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TestDB {
	
	public void Test(Properties p) throws SQLException, ClassNotFoundException{
		DBconnection db = new DBConnection(p);
		String insert="insert into employee (name,birthYear)" + " values('George', 1983)";
		db.initialize();
		dm.makeUpdate(insert);
		db.close();
	}
	
	public void batchTest(Properties p){
		DBconnection db = new DBConnection(p);
		String insert="insert into employee (name,birthYear)" + " values(?,?)";
		PremparedStatement ps = db.makeBatchUpdate(insert);
		
		String name=null;
		String birthYear = null;
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while(line.length()>2){
			StringTokenizer t = new StringTokenizer(line);
			name = t.nextToken();
			birthYear = t.nextToken();
			ps.setString(1, name);
			ps.setString(2, birthYear);
			ps.addBatch();
			line = sc.nextLine();
		}
		ps.executeBatch();
		
		ps.close();
		db.close();
		
	}
	
	public void readTest(Properties p){
		DBconnection db = new DBConnection(p);
		
		String sql = "select name, birthYear from employee";
		db.initialize();
		ResultSet rs = db.makeSingleQuery(sql);
		
		rs.beforeFirst();
		while(rs.next()){
			String name = rs.getString(1);
			String birthYear = rs.getString(2);
			System.out.println(String.format("%s %s\n", name, year));
			
		}
	}
	
	public static void main(String args[]){
		TestDB t = new TestDB;
		Properties p = new Properties();
		p.load(new FileInput Stream(new File("Properties.properties")));
		t.BatchTest(p);
		
		t.test(p);
	}
	re.close()
	db.close();

}
