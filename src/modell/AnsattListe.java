package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;


public class AnsattListe {
	public static ArrayList<Ansatt> alle() throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from ansatt;");
	}

	public static ArrayList<Ansatt> utvidMedId(ArrayList<Ansatt> ansatte , int id) throws NumberFormatException, FileNotFoundException, SQLException, IOException {
		ansatte.add(Ansatt.medId(id));
		return ansatte;
	}

	public static ArrayList<Ansatt> medIder(String ider) throws FileNotFoundException, SQLException, IOException {
		return medSql("select id from ansatt where id in(" + ider + ");");
	}

	public static ArrayList<Ansatt> medAvtaleId(int avtaleId) throws SQLException, FileNotFoundException, IOException {
		return medSql("select aa.ansatt_id from avtale as av,ansatt_avtale as aa where av.id=aa.avtale_id and aa.avtale_id=" + avtaleId + ";");
	}

	/*
	public static ArrayList<Ansatt> medMoteId(int avtaleId) throws SQLException, FileNotFoundException, IOException {
		return medSql("select a.ansatt_id from avtale as a,mote as m where a.mote_id=m.id and  a.id=" + avtaleId + ";");
	}
	*/

	public static ArrayList<Ansatt> utenGruppeIder(String ider) throws FileNotFoundException, SQLException, IOException {
		return medSql("select id from ansatt where id not in (select ansatt_id as id from ansatt_gruppe where gruppe_id in(" + ider + "));");
	}

	public static ArrayList<Ansatt> utvidMedIder(ArrayList<Ansatt> ansatte , String ider) throws FileNotFoundException, SQLException, IOException {
		ArrayList<Ansatt> ekstra = AnsattListe.medIder(ider);
		for (int i = 0; i < ekstra.size(); i++) {
			ansatte.add(ekstra.get(i));
		}
		return ansatte;
	}

	public static ArrayList<Ansatt> medSql(String sql) throws SQLException, FileNotFoundException, IOException {
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
