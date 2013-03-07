package kontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstraktKontroller {
	private BufferedReader stdInn;

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
			if (!tillat_tomhet) {
				s = s.replace("\n", "").replace("\r", "");
			}
			if (s.length() == 0) {
				continue;
			}
		} while (false);
		return s;
	}
}
