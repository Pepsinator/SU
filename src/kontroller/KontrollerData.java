package kontroller;

import java.sql.SQLException;

import modell.Ansatt;

public class KontrollerData {
	private static KontrollerData instans;
	private static Ansatt ansatt;

	public KontrollerData(Ansatt ans) throws SQLException {
		ansatt = ans;
	}

	public static KontrollerData getInstans(Ansatt ans) throws SQLException {
		if (instans == null) {
			synchronized (KontrollerData .class) {
				if (instans == null) {
					instans = new KontrollerData(ans);
				}
			}
		}
		return instans;
	}

	public static KontrollerData getInstans () throws SQLException {
		if (ansatt == null) {
			return null;
		}
		if (instans == null) {
			return KontrollerData.getInstans(ansatt);
		}
		return instans;
	}

	public Ansatt getInnlogga() {
		return ansatt;
	}
}
