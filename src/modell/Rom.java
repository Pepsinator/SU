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

public class Rom {
	private int id;
	private String navn;
	private int kapasitet;
	ArrayList<Avtale> avtaleListe = new ArrayList<Avtale>(); 

	public static Rom medId(int id) throws SQLException,
			FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from rom where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	private static Rom init(ResultSet res) throws SQLException {
		Rom rom = new Rom();
		if (!res.next()) {
			return null;
		}
		rom.setId(res.getInt("id"));
		rom.setNavn(res.getString("navn"));
		rom.setKapasitet(res.getInt("kapasitet"));
		return rom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public int getKapasitet() {
		return kapasitet;
	}

	public void setKapasitet(int kapasitet) {
		this.kapasitet = kapasitet;
	}
	
}
