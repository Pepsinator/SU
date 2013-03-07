package modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;


public class GruppeListe {
	public static ArrayList<Gruppe> alle() throws SQLException {
		return medSql("select id from gruppe;");
	}

	public static ArrayList<Gruppe> medGruppeId(int rotId) throws SQLException {
		return medSql("select id from gruppe where gruppe_id=" + rotId + ";");
	}

	public static ArrayList<Gruppe> medSql(String sql) throws SQLException {
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
