package modell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bibliotek.Database;

public class Status {
	private int id;
	private String navn;
	public Status (int id) throws SQLException, FileNotFoundException, IOException {
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("select * from status where id=" + id + ";");
		ResultSet res = beretning.executeQuery();
		if (!res.next()) {
			this.id = 0;
			this.navn = "";
			return;
		}
		this.id = res.getInt("id");
		this.navn = res.getString("navn");
	}
	private static Map<Integer , Status> instanser;
	public static Status medId(int id) throws SQLException, FileNotFoundException, IOException {
		if (instanser == null) {
			instanser = new HashMap<Integer , Status>();
		}
		if (instanser.get(id) == null) {
			synchronized (Status .class) {
				if (instanser.get(id) == null) {
					instanser.put(id, new Status(id));
				}
			}
		}
		return instanser.get(id);
	}
	public int getId() {
		return id;
	}
	public String getNavn() {
		return navn;
	}
}
