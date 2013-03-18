package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;

public class RomListe {
	public static ArrayList<Rom> alle() throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from rom;");
	}

	public static ArrayList<Rom> medMinimumKapasitet(int kapasitet) throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from rom where kapasitet >= " + kapasitet + ";");
	}

	public static ArrayList<Rom> medSql(String sql) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Rom> rom = new ArrayList<Rom>();
		while (res.next()) {
			rom.add(Rom.medId(res.getInt(1)));
		}
		return rom;
	}
}
