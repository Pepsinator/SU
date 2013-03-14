package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import bibliotek.Database;

public class Avtale {
	private int id;
	private String navn;
	private Date start;
	private Date slutt;
	private String sted;
	private String beskrivelse;

	// latskapsinitialisering
	private int moteleder_id;
	private Ansatt moteleder;

	public Avtale() {
		this.id = 0;
		this.navn = "";
		this.start = new Date();
		this.slutt = new Date();
		this.beskrivelse = "";
		this.moteleder_id = 0;
		this.moteleder = null;
	}

	public static Avtale medId(int id) throws SQLException,
			FileNotFoundException, IOException {
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
		return avt;
	}

	public void oppdater() throws SQLException, FileNotFoundException,
			IOException {
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		String sql = "update avtale set navn=\"" + this.navn
				+ "\",start=from_unixtime("
				+ ((int) (this.start.getTime() * .001))
				+ "),slutt=from_unixtime("
				+ ((int) (this.slutt.getTime() * .001)) + ")" + ",sted=\""
				+ this.sted + "\",beskrivelse=\"" + this.beskrivelse
				+ "\",endra=from_unixtime("
				+ ((int) (new Date().getTime() * .001)) + ") where id="
				+ this.id + ";";
		beretning.executeUpdate(sql);
	}

	public int getMotelederId() throws FileNotFoundException, SQLException,
			IOException {
		if (this.moteleder_id == 0) {
			Connection kobling = Database.getInstans().getKobling();
			PreparedStatement beretning = kobling
					.prepareStatement("select ansatt_id from ansatt_avtale where status_id=1 and avtale_id="
							+ this.id + ";");
			ResultSet res = beretning.executeQuery();
			if (!res.next()) {
				return 0;
			}
			this.moteleder_id = res.getInt(1);
		}
		return this.moteleder_id;
	}

	public Ansatt getMoteleder() throws SQLException, FileNotFoundException,
			IOException {
		int id = this.getMotelederId();
		if (id == 0) {
			return null;
		}
		if (this.moteleder == null) {
			this.moteleder = Ansatt.medId(id);
		}
		return this.moteleder;
	}

	public ArrayList<Ansatt> getDeltakere() throws FileNotFoundException,
			SQLException, IOException {
		return AnsattListe.medAvtaleId(this.id);
	}

	public boolean erDeltakerMedId (int ansattId) throws FileNotFoundException, SQLException, IOException {
		ArrayList<Ansatt> deltakere = this.getDeltakere();
		for (int i = 0; i < deltakere.size(); i++) {
			if (ansattId == deltakere.get(i).getId()) {
				return true;
			}
		}
		return false;
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

	public String getSted() {
		return this.sted;
	}

	public void setSted(String sted) {
		this.sted = sted;
	}

	public String getBeskrivelse() {
		return this.beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
}
