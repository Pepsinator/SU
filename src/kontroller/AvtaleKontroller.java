package kontroller;

import java.text.SimpleDateFormat;
import java.util.Date;

import bibliotek.Database;

import modell.Avtale;
import visning.AvtaleVisning;
import visning.GeneriskVisning;

public class AvtaleKontroller extends AbstraktKontroller {

	public AvtaleKontroller() throws Exception {
		super();
		GeneriskVisning.printTopp();
		this.nyAvtale();
	}
	public AvtaleKontroller(int avtaleId) throws Exception {
		super();
		GeneriskVisning.printTopp();
		this.visAvtale(avtaleId);
	}
	private void nyAvtale () throws Exception {
		System.out.println("==Ny avtale==\n");
		System.out.print("Navn: ");
		String navn = ventStdInn();
		System.out.print("Beskrivelse: ");
		String beskrivelse = ventStdInn(true);
		System.out.println("Tid er på formen: dd.MM.YYYY hh:mm");
		Date start;
		do {
			System.out.print("Starttid: ");
			start = new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(ventStdInn());
		} while (start == null);
		Date slutt;
		do {
			System.out.print("Slutttid: ");
			slutt = new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(ventStdInn());
		} while (slutt == null);
		System.out.print("Er avtalen et møte? (y/?) ");
		boolean er_mote = ventStdInn() == "y" ? true : false;
		if (!er_mote) {
			Avtale avt = Avtale.medId(Database.nyRad("avtale"));
			avt.setNavn(navn);
			avt.setBeskrivelse(beskrivelse);
			avt.setStart(start);
			avt.setSlutt(slutt);
			avt.setAnsattId(KontrollerData.getInstans().getInnlogga().getId());
			avt.oppdater();
			new KalenderKontroller();
			return;
		}
	}
	private void visAvtale (int avtaleId) throws Exception {
		Avtale avt = Avtale.medId(avtaleId);
		if (avt == null) {
			System.out.println("Valgt avtale fins ikke.");
			return;
		}
		AvtaleVisning.visAvtale(avt);
		GeneriskVisning.printKommando("k", "kalender");
		GeneriskVisning.printKommando("q", "avslutt");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'k':
				new KalenderKontroller();
				return;
			case 'q':
				this.avslutt();
			default:
				break;
			}
		} while (true);
	}
}
