package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bibliotek.Database;
import bibliotek.Funksjon;
import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.Mote;
import visning.AvtaleVisning;
import visning.GeneriskVisning;

public class AvtaleKontroller extends AbstraktKontroller {

	public AvtaleKontroller() throws Exception {
		super();
		this.nyAvtale();
	}
	public AvtaleKontroller(int avtaleId) throws Exception {
		super();
		GeneriskVisning.printTopp();
		this.visAvtale(avtaleId);
	}
	private void nyAvtale () throws Exception {
		this.endreAvtale(0);
	}
	private void endreAvtale (int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = null;
		int ansattId = KontrollerData.getInstans().getInnlogga().getId();
		System.out.println("==" + (avtaleId > 0 ? "Endre" : "Ny") + " avtale==\n");
		if (avtaleId > 0) {
			avt = Avtale.medId(avtaleId);
			ansattId = avt.getMotelederId();
			System.out.println("Gammelt navn: " + avt.getNavn());
		}
		System.out.print("Navn: ");
		String navn = ventStdInn();
		if (avtaleId > 0) {
			System.out.println("Gammel beskrivelse: " + avt.getBeskrivelse());
		}
		System.out.print("Beskrivelse: ");
		String beskrivelse = ventStdInn(true);
		System.out.println("Tid er på formen: dd.MM.yyyy hh:mm");
		if (avtaleId > 0) {
			System.out.println("Gammel starttid: " + avt.getStart());
		}
		Date start;
		do {
			System.out.print("Starttid: ");
			try {
				start = new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(ventStdInn());
			}
			catch (ParseException u) {
				start = null;
			}
		} while (start == null);
		if (avtaleId > 0) {
			System.out.println("Gammel sluttid: " + avt.getStart());
		}
		Date slutt;
		do {
			System.out.print("Slutttid: ");
			try {
				slutt = new SimpleDateFormat("dd.MM.yyyy hh:mm").parse(ventStdInn());
			}
			catch (ParseException u) {
				slutt = null;
			}
		} while (slutt == null);
		boolean er_mote = false;
		Mote mote = null;
		if (avtaleId > 0) {
			mote = Mote.medAvtaleId(avtaleId);
			er_mote = mote != null;
		}
		else {
			System.out.print("Er avtalen et møte? (y/*) ");
			er_mote = this.ventStdInn().charAt(0) == 'y';
		}
		if (!er_mote) {
			avt = Avtale.medId(Database.nyRad("avtale"));
			this.oppdaterAvtale(avt , navn , beskrivelse , start , slutt , ansattId);
			new KalenderKontroller();
			return;
		}
		System.out.println("Hvilke grupper skal være med på møtet? Skriv id-ene separert med komma:");
		GeneriskVisning.printGrupper();
		String inn;
		ArrayList<Ansatt> ansatte;
		do {
			inn = ventStdInn().replaceAll("[,]{2,}", ",").replaceAll("\\s*" , "");
			if (Funksjon.sjekkIder(inn)) {
				ansatte = AnsattListe.utenGruppeIder(inn);
				for (int i = 0; i < ansatte.size(); i++) {
					if (ansatte.get(i).getId() == ansattId) {
						ansatte.remove(i);
						break;
					}
				}
				if (ansatte.size() > 0) {
					break;
				}
				System.out.println("Ingen ansatte. Velg igjen.");
				continue;
			}
			System.out.println("Klarte ikke å tolke. Prøv igjen.");
		} while (true);
		System.out.println("Hvilke ansatte (utenom gruppene) skal være med på møtet? Skriv id-ene separert med komma:");
		GeneriskVisning.printAnsatte(ansatte);
		do {
			inn = ventStdInn().replaceAll("[,]{2,}", ",").replaceAll("\\s*" , "");
			if (Funksjon.sjekkIder(inn)) {
				ansatte = AnsattListe.utvidMedIder(ansatte , inn);
				for (int i = 0; i < ansatte.size(); i++) {
					if (ansatte.get(i).getId() == ansattId) {
						ansatte.remove(i);
						break;
					}
				}
				if (ansatte.size() > 0) {
					break;
				}
				System.out.println("Ingen ansatte. Velg igjen.");
				continue;
			}
			System.out.println("Klarte ikke å tolke. Prøv igjen.");
		} while (true);
		ansatte = AnsattListe.utvidMedId(ansatte, ansattId);
		System.out.println("Hvilket rom med minimum " + ansatte.size() + " i kapasitet?");
	}
	private void oppdaterAvtale (Avtale avt , String navn , String beskrivelse , Date start , Date slutt , int ansattId) throws FileNotFoundException, SQLException, IOException, Exception {
		avt.setNavn(navn);
		avt.setBeskrivelse(beskrivelse);
		avt.setStart(start);
		avt.setSlutt(slutt);
		avt.oppdater();
	}
	private void visAvtale (int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = Avtale.medId(avtaleId);
		if (avt == null) {
			System.out.println("Valgt avtale fins ikke.");
			return;
		}
		AvtaleVisning.visAvtale(avt);
		GeneriskVisning.printKommando("e", "endre");
		GeneriskVisning.printKommando("s", "slett");
		GeneriskVisning.printKommando("k", "kalender");
		GeneriskVisning.printKommando("q", "avslutt");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'e':
				this.endreAvtale(avtaleId);
				return;
			case 's':
				this.slettAvtale(avtaleId);
				return;
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
	private void slettAvtale(int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = Avtale.medId(avtaleId);
		if (avt == null) {
			System.out.println("Valgt avtale fins ikke.");
			return;
		}
		System.out.println("Slette avtale \"" + avt.getNavn() + "\"? (y/*)");
		if (ventStdInn(true) != "y") {
			this.visAvtale(avtaleId);
			return;
		}
		Connection kobling = Database.getInstans().getKobling();
		PreparedStatement beretning = kobling
				.prepareStatement("delete from avtale where id=" + avtaleId + ";");
		beretning.executeQuery();
		System.out.println("Avtale er sletta. Trykk linjeskift...");
		this.ventStdInn(true);
		new KalenderKontroller();
		return;
	}
}
