package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bibliotek.Database;

public class Alarm {
	private int id;
	private int avtale_id;
	private int ansatt_id;
	private int tidForAvtale;  	// tid i sekunder før avtale

	Alarm(){
		this.id = 0;
		this.avtale_id = 0;
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
		alarm.setAvtale_id(res.getInt("avtale_id"));
		alarm.setAnsatt_id(res.getInt("ansatt_id"));
		alarm.setTidForAvtale(res.getInt("tid_for"));
		alarm.setId(res.getInt("id"));
		return alarm;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getAvtale_id() {
		return avtale_id;
	}

	public void setAvtale_id(int avtale_id) {
		this.avtale_id = avtale_id;
	}

	public int getAnsatt_id() {
		return ansatt_id;
	}

	public void setAnsatt_id(int ansatt_id) {
		this.ansatt_id = ansatt_id;
	}

	public int getTidForAvtale() {
		return tidForAvtale;
	}
	
	public void setTidForAvtale(int tidForAvtale) {
		this.tidForAvtale = tidForAvtale;
	}
	
}
