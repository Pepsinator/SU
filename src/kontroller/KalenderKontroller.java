package kontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bibliotek.Funksjon;

import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.AvtaleListe;
import modell.KontrollerData;

import visning.AlarmVisning;
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
		Date ukestart = new Date((new Date((new SimpleDateFormat("yyyy/MM/dd")).format(tid))).getTime() - 86400000 * (kal.get(Calendar.DAY_OF_WEEK) - 2));
		@SuppressWarnings("deprecation")
		Date ukeslutt = new Date((new Date((new SimpleDateFormat("yyyy/MM/dd")).format(tid))).getTime() - 86400000 * (kal.get(Calendar.DAY_OF_WEEK) - 9));
		ArrayList<Avtale> avtaler = AvtaleListe.medAnsattIdTidsrom(this.ansatt.getId(), ukestart , ukeslutt);
		for (int i = 0; i < avtaler.size(); i++) {
			String status = "";												//Skriver ut statusen for avtalen i kalenderVisning
			int lengde = avtaler.get(i).getNavn().length();					//
			String mellomrom = Funksjon.strRepeat(" " , 18-lengde);			//
			int s = avtaler.get(i).getStatusIdMedAnsattId(ansatt.getId());	//
			if (s == 2 || s == 5){											//
				status = mellomrom + "(Krever svar)";						//
			}																//
			GeneriskVisning.printKommando("" + avtaler.get(i).getId() , avtaler.get(i).getNavn() + status);
		}
		if (avtaler.size() > 0) {
			System.out.println();
		}
		
		GeneriskVisning.printKommando("a", "vis som ansatt");
		GeneriskVisning.printKommando("n", "ny avtale");
		GeneriskVisning.printKommando("<", "forrige uke");
		GeneriskVisning.printKommando(">", "neste uke");
		GeneriskVisning.printKommando("v", "vis/rediger varsel");
		GeneriskVisning.printKommando("q", "avslutt");
		
		
		String inn;
		int avtaleId;
		do {
			inn = this.ventStdInn();
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
		case 'v':
			new AlarmKontroller();
			return;
		case '<':
			this.tid = new Date(this.tid.getTime() - 86400000 * 7);
			break;
		case '>':
			this.tid = new Date(this.tid.getTime() + 86400000 * 7);
			break;
		}
		this.visKalender();
	}
	private void velgAnsatt () throws SQLException, IOException {
		Ansatt ansatt = null;
		do {
			GeneriskVisning.printTopp();
			System.out.println("Velg en ansatt:\n");
			GeneriskVisning.printAnsatte(AnsattListe.alle());
			try {
				ansatt = Ansatt.medId(Integer.parseInt(ventStdInn()));
			}
			catch (NumberFormatException u) {
				continue;
			}
		} while (ansatt == null);
		this.ansatt = ansatt;
	}
}
