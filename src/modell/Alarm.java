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

public class Alarm {
	private int id;
	private int avtale_id;
	private int ansatt_id;
	private int tidForAvtale;  	// tid i sekunder før avtale
	private Avtale avtale;

	Alarm(){
		this.id = 0;
		this.avtale_id = 0;
		this.ansatt_id = 0;
		this.tidForAvtale = 0;
		this.avtale = null;
	}
	
	// henter alarm med spesifikk id fra databasen
	public static Alarm medId(int alarmId) throws FileNotFoundException, SQLException, IOException{
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from alarm where id=" + alarmId + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
		
	}
	
	// henter alarm med en ansatt sin avtale
	public static Alarm medAnsattIdAvtaleId(int ansattId, int avtaleId) throws FileNotFoundException, SQLException, IOException{
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from alarm where ansatt_id=" + ansattId + " and avtale_id=" + avtaleId + ";");
		ResultSet res = beretning.executeQuery();
		return init(res);
	}
	
	//initialiserer et alarmobjekt
	private static Alarm init(ResultSet res) throws SQLException {
		if (!res.next()) {
			return null;
		}
		Alarm alarm = new Alarm();
		alarm.setAvtaleId(res.getInt("avtale_id"));
		alarm.setAnsattId(res.getInt("ansatt_id"));
		alarm.setTidForAvtale(res.getInt("tid_for"));
		alarm.setId(res.getInt("id"));
		return alarm;
	}
	
	public void oppdater(int tidFor, int avtale_id) throws SQLException, FileNotFoundException,
	IOException {
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		String sql = "UPDATE alarm SET tid_for=" + tidFor
				+ ", ansatt_id=" + KontrollerData.getInstans().getInnlogga().getId()
				+ ", avtale_id=" + avtale_id 
				+ " where id=" 
				+ this.id + ";";
		beretning.executeUpdate(sql);
	}
	
	public void slett() throws FileNotFoundException, SQLException, IOException{
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		
		String sql = "DELETE FROM alarm where id=" + this.id + ";";
		beretning.execute(sql);
	}

	public int getSekTilStart () throws FileNotFoundException, SQLException, IOException {
		return (int) (new Date().getTime() - this.getAvtale().getStart().getTime()) / 1000;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getAvtaleId() {
		return avtale_id;
	}

	public Avtale getAvtale () throws FileNotFoundException, SQLException, IOException {
		if (this.avtale == null) {
			this.avtale = Avtale.medId(this.avtale_id);
		}
		return this.avtale;
	}

	public void setAvtaleId(int avtale_id) {
		this.avtale_id = avtale_id;
	}

	public int getAnsattId() {
		return ansatt_id;
	}

	public void setAnsattId(int ansatt_id) {
		this.ansatt_id = ansatt_id;
	}

	public int getTidForAvtale() {
		return tidForAvtale;
	}
	
	public void setTidForAvtale(int tidForAvtale) {
		this.tidForAvtale = tidForAvtale;
	}
	
	
}
