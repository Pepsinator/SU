package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import bibliotek.Database;

public class RomListe {
	public static ArrayList<Rom> alle() throws SQLException,
			FileNotFoundException, IOException {
		return medSql("select id from rom;");
	}

	public static ArrayList<Rom> medMinimumKapasitet(int kapasitet)
			throws SQLException, FileNotFoundException, IOException {
		return medSql("select id from rom where kapasitet >= " + kapasitet
				+ ";");
	}

	public static ArrayList<Rom> ledigeMedMinimumKapasitet(int kapasitet,
			Date start, Date slutt) throws SQLException, FileNotFoundException,
			IOException {
		String starta = "from_unixtime(" + ((int) (start.getTime() * .001))
				+ ")";
		String slutta = "from_unixtime(" + ((int) (slutt.getTime() * .001))
				+ ")";
		return medSql("select id from rom where kapasitet >= "
				+ kapasitet
				+ " and id NOT IN("
				+ "select r.id from rom as r,avtale as a where a.aktiv=1 and r.id=a.rom_id and (("
				+ starta + " <= a.start and a.start <= " + slutta + ") or ("
				+ starta + " <= a.slutt and a.slutt <= " + slutta + ") or ("
				+ starta + " <= a.start and a.slutt <= " + slutta
				+ ") or (a.start <= " + starta + " and " + slutta
				+ " <= a.slutt)));");
	}

	public static ArrayList<Rom> medSql(String sql) throws SQLException,
			FileNotFoundException, IOException {
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
