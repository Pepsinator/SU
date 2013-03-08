package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bibliotek.Database;


public class Gruppe {
	private int id;
	private String navn;
	private int rot_id;

	Gruppe() {
		this.id = 0;
		this.navn = "";
		this.rot_id = 0;
	}

	public static Gruppe medId(int id) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from gruppe where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	private static Gruppe init(ResultSet res) throws SQLException {
		Gruppe grp = new Gruppe();
		if (!res.next()) {
			return null;
		}
		grp.setId(res.getInt("id"));
		grp.setNavn(res.getString("navn"));
		grp.setRotId(res.getInt("rot_id"));
		return grp;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNavn() {
		return this.navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public int getRotId() {
		return this.rot_id;
	}

	public void setRotId(int rot_id) {
		this.rot_id = rot_id;
	}
}
