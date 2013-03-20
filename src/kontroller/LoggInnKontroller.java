package kontroller;

import java.math.BigInteger;
import java.security.MessageDigest;

import modell.Ansatt;
import modell.KontrollerData;

public class LoggInnKontroller extends AbstraktKontroller {

	public LoggInnKontroller() throws Exception {
		super();
		String bruker;
		String passord;
		Ansatt res;
		do {
			System.out.print("Brukernavn: ");
			bruker = this.ventStdInn();
			System.out.print("Passord: ");
			passord = this.ventStdInn();
			MessageDigest kryptert = MessageDigest.getInstance("MD5");
			kryptert.update(passord.getBytes("UTF-8"));
			res = Ansatt.medLoggInn(bruker, new BigInteger(kryptert.digest()).toString(16));
			if (res == null) {
				System.out.println("Feil brukernavn/passord, prøv igjen.\n");
				continue;
			}
			break;
		} while (true);
		KontrollerData.getInstans(res);
		new KalenderKontroller();
	}

	public static void main(String[] arg) throws Exception {
		new LoggInnKontroller();
	}
}
