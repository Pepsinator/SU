package modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;


public class AnsattListe {
	public static ArrayList<Ansatt> alle() throws SQLException {
		return medSql("select id from ansatt;");
	}

	public static ArrayList<Ansatt> medMoteId(int avtaleId) throws SQLException {
		return medSql("select a.ansatt_id from avtale as a,mote as m where a.mote_id=m.id and  a.id=" + avtaleId + ";");
	}

	public static ArrayList<Ansatt> medSql(String sql) throws SQLException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Ansatt> ansatte = new ArrayList<Ansatt>();
		while (res.next()) {
			ansatte.add(Ansatt.medId(res.getInt(1)));
		}
		return ansatte;
	}
}
