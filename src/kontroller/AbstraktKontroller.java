package kontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import bibliotek.Funksjon;

public abstract class AbstraktKontroller {
	private BufferedReader stdInn;
	//adasd

	public AbstraktKontroller() {
		this.stdInn = new BufferedReader(new InputStreamReader(System.in));
	}

	public String ventStdInn() throws IOException {
		return ventStdInn(false);
	}

	public String ventStdInn(boolean tillat_tomhet) throws IOException {
		String s;
		do {
			s = stdInn.readLine();
			if (s == null) {
				continue;
			}
			s = s.replace("\n", "").replace("\r", "");
			if (s.length() == 0 && !tillat_tomhet) {
				continue;
			}
			break;
		} while (true);
		return s;
	}

	protected void avslutt () {
		System.out.println(Funksjon.strRepeat("\n" , 100) + "Avslutta");
		System.exit(0);
	}
}
