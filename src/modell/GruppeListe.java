package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;


public class GruppeListe {
	public static ArrayList<Gruppe> alle() throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from gruppe;");
	}

	public static ArrayList<Gruppe> medRotId(int rotId) throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from gruppe where rot_id=" + (rotId == 0 ? "0 or rot_id is null" : rotId) + ";");
	}

	public static ArrayList<Gruppe> medSql(String sql) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Gruppe> grupper = new ArrayList<Gruppe>();
		while (res.next()) {
			grupper.add(Gruppe.medId(res.getInt(1)));
		}
		return grupper;
	}
}
