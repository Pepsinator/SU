package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import bibliotek.Database;

public class Avtale {
	private int id;
	private String navn;
	private Date start;
	private Date slutt;
	private int ansatt_id;
	private int mote_id;
	private String beskrivelse;

	// latskapsinitialisering
	private Ansatt ansatt;
	private Mote mote;

	public Avtale() {
		this.id = 0;
		this.navn = "";
		this.start = new Date();
		this.slutt = new Date();
		this.ansatt_id = 0;
		this.mote_id = 0;
		this.beskrivelse = "";
		this.ansatt = null;
		this.mote = null;
	}

	public static Avtale medId(int id) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from avtale where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}

	private static Avtale init(ResultSet res) throws SQLException {
		Avtale avt = new Avtale();
		if (!res.next()) {
			return null;
		}
		avt.setId(res.getInt("id"));
		avt.setNavn(res.getString("navn"));
		if (res.getTimestamp("start") != null) {
			avt.setStart(new Date(res.getTimestamp("start").getTime()));
		}
		if (res.getTimestamp("slutt") != null) {
			avt.setSlutt(new Date(res.getTimestamp("slutt").getTime()));
		}
		avt.setAnsattId(res.getInt("ansatt_id"));
		avt.setMoteId(res.getInt("mote_id"));
		return avt;
	}

	public void oppdater() throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		String sql = "update avtale set navn=\"" + this.navn
				+ "\",start=from_unixtime("
				+ ((int) (this.start.getTime() * .001))
				+ "),slutt=from_unixtime("
				+ ((int) (this.start.getTime() * .001)) + "),ansatt_id="
				+ this.ansatt_id;
		if (this.mote_id != 0) {
			sql += ",mote_id=" + this.mote_id;
		}
		sql += ",beskrivelse=\"" + this.beskrivelse + "\" where id=" + this.id
				+ ";";
		beretning.executeUpdate(sql);
	}

	public Ansatt getAnsatt() throws SQLException, FileNotFoundException, IOException {
		if (this.ansatt == null) {
			this.ansatt = Ansatt.medId(this.ansatt_id);
		}
		return this.ansatt;
	}

	public Mote getMote() throws SQLException, FileNotFoundException, IOException {
		if (this.mote == null) {
			this.mote = Mote.medId(this.mote_id);
		}
		return this.mote;
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

	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getSlutt() {
		return this.slutt;
	}

	public void setSlutt(Date slutt) {
		this.slutt = slutt;
	}

	public int getAnsattId() {
		return this.ansatt_id;
	}

	public void setAnsattId(int ansatt_id) {
		this.ansatt = null;
		this.ansatt_id = ansatt_id;
	}

	public int getMoteId() {
		return this.mote_id;
	}

	public void setMoteId(int mote_id) {
		this.mote = null;
		this.mote_id = mote_id;
	}

	public String getBeskrivelse() {
		return this.beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
}
