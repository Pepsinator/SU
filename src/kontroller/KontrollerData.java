package kontroller;

import java.sql.Connection;
import java.sql.SQLException;

import bibliotek.Database;


import modell.Ansatt;

public class KontrollerData {
	private Connection kobling;
	private Ansatt innlogga;

	public KontrollerData(Ansatt ans) throws SQLException {
		this.kobling = Database.getInstans().getKobling();
		this.innlogga = ans;
	}

	public Connection getKobling () {
		return this.kobling;
	}

	public Ansatt getInnlogga() {
		return this.innlogga;
	}
}
