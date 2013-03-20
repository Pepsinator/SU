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

public class AvtaleListe {
	public static ArrayList<Avtale> alle() throws SQLException,
			FileNotFoundException, IOException {
		return medSql("select id from avtale;");
	}

	public static ArrayList<Avtale> medAnsattIdTidsrom(int ansattId,
			Date start, Date slutt) throws SQLException, FileNotFoundException,
			IOException {
		String starta = "from_unixtime(" + ((int) (start.getTime() * .001))
				+ ")";
		String slutta = "from_unixtime(" + ((int) (slutt.getTime() * .001))
				+ ")";
		return medSql("select av.id from avtale as av,ansatt_avtale as aa where av.aktiv=1 and av.id=aa.avtale_id and aa.ansatt_id="
				+ ansattId
				+ " and (("
				+ starta
				+ " <= av.start and av.start < "
				+ slutta
				+ ") or ("
				+ starta
				+ " <= av.slutt and av.slutt < "
				+ slutta
				+ ") or ("
				+ starta
				+ " <= av.start and av.slutt < "
				+ slutta
				+ ") or (av.start <= "
				+ starta
				+ " and "
				+ slutta
				+ " < av.slutt));");
	}

	public static ArrayList<Avtale> aktiveMedAnsattIdTidsrom(int ansattId,
			Date start, Date slutt) throws SQLException, FileNotFoundException,
			IOException {
		String starta = "from_unixtime(" + ((int) (start.getTime() * .001))
				+ ")";
		String slutta = "from_unixtime(" + ((int) (slutt.getTime() * .001))
				+ ")";
		return medSql("select av.id from avtale as av,ansatt_avtale as aa where av.aktiv=1 and av.id=aa.avtale_id and aa.ansatt_id="
				+ ansattId
				+ " and (("
				+ starta
				+ " <= av.start and av.start < "
				+ slutta
				+ ") or ("
				+ starta
				+ " <= av.slutt and av.slutt < "
				+ slutta
				+ ") or ("
				+ starta
				+ " <= av.start and av.slutt < "
				+ slutta
				+ ") or (av.start <= "
				+ starta
				+ " and "
				+ slutta
				+ " < av.slutt));");
	}

	public static ArrayList<Avtale> medSql(String sql) throws SQLException,
			FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Avtale> avtaler = new ArrayList<Avtale>();
		while (res.next()) {
			avtaler.add(Avtale.medId(res.getInt(1)));
		}
		return avtaler;
	}

	public static String oppramsIder(ArrayList<Avtale> avtaler) {
		String res = "";
		for (int i = 0; i < avtaler.size(); i++) {
			if (i > 0) {
				res += ",";
			}
			res += avtaler.get(i).getId();
		}
		return res;
	}
}
