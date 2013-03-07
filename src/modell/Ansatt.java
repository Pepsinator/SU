package modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bibliotek.Database;


public class Ansatt {
	private int id;
	private String bruker;
	private String passord;
	private String navn;

	public Ansatt() {
		this.id = 0;
		this.bruker = "";
		this.passord = "";
		this.navn = "";
	}

	public static Ansatt medId(int id) throws SQLException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from ansatt where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	public static Ansatt medLoggInn(String bruker, String passord)
			throws SQLException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from ansatt where bruker=\"" + bruker + "\" and passord=\"" + passord + "\";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	private static Ansatt init(ResultSet res) throws SQLException {
		if (!res.next()) {
			return null;
		}
		Ansatt ans = new Ansatt();
		ans.setId(res.getInt("id"));
		ans.setBruker(res.getString("bruker"));
		ans.setPassord(res.getString("passord"));
		ans.setNavn(res.getString("navn"));
		return ans;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBruker() {
		return bruker;
	}

	public void setBruker(String bruker) {
		this.bruker = bruker;
	}

	public void setPassord(String passord) {
		this.passord = passord;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}
}
