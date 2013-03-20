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

public class AlarmListe {

	// returner en liste med alarmer
	public static ArrayList<Alarm> alle() throws FileNotFoundException,
			SQLException, IOException {
		return medSql("select id from alarm;");
	}

	// returner en liste med alarmer for vaklgt bruker
	public static ArrayList<Alarm> medAnsattId(int ansattId)
			throws FileNotFoundException, SQLException, IOException {
		return medSql("select id from alarm where ansatt_id=" + ansattId + ";");
	}

	// returner en liste med aktuelle/aktive alarmer for vaklgt bruker
	public static ArrayList<Alarm> aktuelleMedAnsattId(int ansattId)
			throws FileNotFoundException, SQLException, IOException {
		// avtalen kan ikke være ferdig, og må det være maks tidForAvtale igjen
		// til avtalen
		String naotid = "from_unixtime("
				+ ((int) ((new Date()).getTime() * .001)) + ")";
		String naotidMinusTidFor = "from_unixtime("
				+ ((int) ((new Date()).getTime() * .001)) + " - al.tid_for)";
		String sql = "select al.id from alarm as al,avtale as av where al.avtale_id=av.id and al.ansatt_id="
				+ ansattId
				+ " and "
				+ naotid
				+ " < av.slutt and "
				+ naotidMinusTidFor + " < av.start;";
		System.out.println(sql + "\n");
		return medSql(sql);
	}

	// henter alarmer fra databasen
	public static ArrayList<Alarm> medSql(String sql) throws SQLException,
			FileNotFoundException, IOException {
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
