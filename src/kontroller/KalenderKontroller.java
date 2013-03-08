package kontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.AvtaleListe;

import visning.GeneriskVisning;
import visning.KalenderVisning;

public class KalenderKontroller extends AbstraktKontroller {
	private Date tid;
	private Ansatt ansatt;

	KalenderKontroller() throws Exception {
		super();
		this.ansatt = KontrollerData.getInstans().getInnlogga();
		this.tid = new Date();
		this.visKalender();
	}

	private void visKalender() throws Exception {
		KalenderVisning.print(this.tid , this.ansatt);
		Calendar kal = Calendar.getInstance();
		System.out.println();
		@SuppressWarnings("deprecation")
		Date ukestart = new Date((new Date((new SimpleDateFormat("YYYY/MM/dd")).format(tid))).getTime() - 86400000 * (kal.get(Calendar.DAY_OF_WEEK) - 2));
		@SuppressWarnings("deprecation")
		Date ukeslutt = new Date((new Date((new SimpleDateFormat("YYYY/MM/dd")).format(tid))).getTime() - 86400000 * (kal.get(Calendar.DAY_OF_WEEK) - 9));
		ArrayList<Avtale> avtaler = AvtaleListe.medAnsattIdTidsrom(this.ansatt.getId(), ukestart , ukeslutt);
		for (int i = 0; i < avtaler.size(); i++) {
			GeneriskVisning.printKommando("" + avtaler.get(i).getId() , avtaler.get(i).getNavn());
		}
		if (avtaler.size() > 0) {
			System.out.println();
		}
		GeneriskVisning.printKommando("a", "vis som ansatt");
		GeneriskVisning.printKommando("n", "ny avtale");
		GeneriskVisning.printKommando("<", "forrige uke");
		GeneriskVisning.printKommando(">", "neste uke");
		GeneriskVisning.printKommando("q", "avslutt");
		String inn = this.ventStdInn();
		do {
			int avtaleId;
			try {
				avtaleId = Integer.parseInt(inn);
			}
			catch (NumberFormatException u) {
				break;
			}
			new AvtaleKontroller(avtaleId);
			return;
		} while (false);
		switch (inn.charAt(0)) {
		case 'a':
			this.velgAnsatt();
			break;
		case 'n':
			new AvtaleKontroller();
			return;
		case 'q':
			this.avslutt();
			return;
		case '<':
			this.tid = new Date(this.tid.getTime() - 86400 * 7000);
			break;
		case '>':
			this.tid = new Date(this.tid.getTime() + 86400 * 7000);
			break;
		}
		this.visKalender();
	}
	private void velgAnsatt () throws SQLException, NumberFormatException, IOException {
		Ansatt ansatt;
		do {
			GeneriskVisning.printTopp();
			GeneriskVisning.printAnsatte(AnsattListe.alle());
			ansatt = Ansatt.medId(Integer.parseInt(ventStdInn()));
		} while (ansatt == null);
		this.ansatt = ansatt;
	}
}
