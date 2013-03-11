package kontroller;

import visning.GeneriskVisning;
import modell.Ansatt;

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
			res = Ansatt.medLoggInn(bruker, passord);
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
