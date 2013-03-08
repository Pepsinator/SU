package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bibliotek.Database;

public class Mote {
	private int id;
	private int ansatt_id;

	//latskapsinitialisering
	private Ansatt ansatt;

	public Mote () {
		this.id = 0;
		this.ansatt_id = 0;
		this.ansatt = null;
	}

	public static Mote medId(int id) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from mote where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	private static Mote init(ResultSet res) throws SQLException {
		Mote mote = new Mote();
		if (!res.next()) {
			return null;
		}
		mote.setId(res.getInt("id"));
		return mote;
	}

	public Ansatt getAnsatt () throws SQLException, FileNotFoundException, IOException {
		if (this.ansatt == null) {
			this.ansatt = Ansatt.medId(this.ansatt_id);
		}
		return this.ansatt;
	}

	public int getId () {
		return this.id;
	}

	public void setId (int id) {
		this.id = id;
	}
}
