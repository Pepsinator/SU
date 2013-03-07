package kontroller;

import visning.GeneriskVisning;
import modell.Ansatt;

public class LoggInnKontroller extends AbstraktKontroller {
	private KontrollerData kd;

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
				System.out.println("Feil brukernavn/passord, pr�v igjen.\n");
				continue;
			}
			break;
		} while (true);
		this.kd = new KontrollerData(res);
		new KalenderKontroller(this.kd);
	}

	public static void main(String[] arg) throws Exception {
		new LoggInnKontroller();
	}
}
