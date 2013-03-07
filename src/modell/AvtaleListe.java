package modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import bibliotek.Database;


public class AvtaleListe {
	public static ArrayList<Avtale> alle() throws SQLException {
		return medSql("select id from avtale;");
	}

	public static ArrayList<Avtale> medTidsrom (Date start, Date slutt) throws SQLException {
		String starta = "from_unixtime(" + ((int) (start.getTime() * .001)) + ")";
		String slutta = "from_unixtime(" + ((int) (slutt.getTime() * .001)) + ")";
		return medSql("select id from avtale where (" + starta + " <= start and start < " + slutta + ") or (" + starta + " <= slutt and slutt < " + slutta + ") or (" + starta + " <= start and slutt < " + slutta + ") or (start <= " + starta + " and " + slutta + " < slutt);");
	}

	public static ArrayList<Avtale> medSql(String sql) throws SQLException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling.prepareStatement(sql);
		ResultSet res = beretning.executeQuery();
		ArrayList<Avtale> avtaler = new ArrayList<Avtale>();
		while (res.next()) {
			avtaler.add(Avtale.medId(res.getInt(1)));
		}
		return avtaler;
	}

	public static String oppramsIder (ArrayList<Avtale> avtaler) {
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
