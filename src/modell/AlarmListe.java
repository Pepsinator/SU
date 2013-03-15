package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;

public class AlarmListe {

	//returner en liste med alarmer
	public static ArrayList<Alarm> alle() throws FileNotFoundException, SQLException, IOException{
		return medSql("select id from alarm;");
	}

	//returner en liste med alarmer for vaklgt bruker
	public static ArrayList<Alarm> medAnsattId(int ansattId) throws FileNotFoundException, SQLException, IOException{
		return medSql("select id from alarm where ansatt_id=" + ansattId + ";");
	}
	
	//henter alarmer fra databasen
	public static ArrayList<Alarm> medSql(String sql) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Alarm> alarmer = new ArrayList<Alarm>();
		while (res.next()) {
			alarmer.add(Alarm.medId(res.getInt(1)));
		}
		return alarmer;
	}
	
}
