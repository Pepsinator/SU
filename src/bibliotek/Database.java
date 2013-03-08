package bibliotek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
	private static Database instans;
	private Connection kobling;
	public static Database getInstans() throws SQLException {
		if (instans == null) {
			synchronized (Database .class) {
				if (instans == null) {
					instans = new Database();
				}
			}
		}
		return instans;
	}
	public Connection getKobling () throws SQLException, FileNotFoundException, IOException {
		if (this.kobling == null) {
			Properties egenskaper = new Properties();
			egenskaper.load(new FileInputStream(new File("database.properties")));
			String adr = egenskaper.getProperty("adr");
			String bruker = egenskaper.getProperty("bruker");
			String pass = egenskaper.getProperty("pass");
			this.kobling = DriverManager.getConnection(adr, bruker, pass);
		}
		return this.kobling;
	}
	public static int nyRad (String tabell) throws Exception {
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		beretning.executeUpdate("insert into " + tabell + " (id) values(null);" , Statement.RETURN_GENERATED_KEYS);
		ResultSet res = beretning.getGeneratedKeys();
		if (!res.next()) {
			throw new Exception("Rad ble satt inn, men har ukjent id.");
		}
		return res.getInt(1);
	}
}
