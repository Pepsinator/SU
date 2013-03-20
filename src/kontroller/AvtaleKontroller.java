package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bibliotek.Database;
import bibliotek.Funksjon;
import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.KontrollerData;
import modell.Rom;
import modell.RomListe;
import modell.Status;
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

	private void nyAvtale() throws Exception {
		this.endreAvtale(0);
	}

	private void endreAvtale(int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = null;
		int ansattId = KontrollerData.getInstans().getInnlogga().getId();
		if (avtaleId > 0) {
			avt = Avtale.medId(avtaleId);
			if (avt == null) {
				System.out.println("Valgt avtale finnes ikke.");
				return;
			}
			if (ansattId != avt.getMotelederId()) {
				System.out.println("Du har ikke adgang.");
				return;
			}
		}
		System.out.println("==" + (avtaleId > 0 ? "Endre" : "Ny")
				+ " avtale==\n");
		if (avtaleId > 0) {
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
				start = new SimpleDateFormat("dd.MM.yyyy hh:mm")
						.parse(ventStdInn());
			} catch (ParseException u) {
				start = null;
			}
		} while (start == null);
		if (avtaleId > 0) {
			System.out.println("Gammel sluttid: " + avt.getSlutt());
		}
		Date slutt;
		do {
			System.out.print("Slutttid: ");
			try {
				slutt = new SimpleDateFormat("dd.MM.yyyy hh:mm")
						.parse(ventStdInn());
				if( start.getTime() >= slutt.getTime()){
					System.out.println("Slutttid må være etter starttid!");
					slutt = null;
				}
			} catch (ParseException u) {
				slutt = null;
			}
		} while (slutt == null);
		boolean moteinfo = false;
		boolean er_mote = false;
		if (avtaleId == 0) {
			System.out.print("Er avtalen et møte? (y/*) ");
			moteinfo = this.ventStdInn().charAt(0) == 'y';
		}
		else {
			er_mote = avt.getDeltakere().size() > 0;
		}
		String sted = "";
		int romId = 0;
		ArrayList<Ansatt> ansatte = null;
		String inn;
		if (moteinfo) {
			ArrayList<Ansatt> u_ansatte;
			System.out
					.println("Hvilke grupper skal være med på møtet? Skriv id-ene separert med komma:");
			GeneriskVisning.printGrupper();
			do {
				inn = ventStdInn(true).replaceAll("[,]{2,}", ",").replaceAll(
						"\\s*", "");
				if (Funksjon.sjekkIder(inn)) {
					ansatte = AnsattListe.medGruppeIder(inn);
					u_ansatte = AnsattListe.utenGruppeIder(inn);
					for (int i = 0; i < u_ansatte.size(); i++) {
						if (u_ansatte.get(i).getId() == ansattId) {
							u_ansatte.remove(i);
							break;
						}
					}
					break;
				}
				System.out.println("Klarte ikke å tolke. Prøv igjen.");
			} while (true);
			System.out
					.println("Hvilke ansatte (utenom gruppene) skal være med på møtet? Skriv id-ene separert med komma:");
			GeneriskVisning.printAnsatte(u_ansatte);
			do {
				inn = ventStdInn(true).replaceAll("[,]{2,}", ",").replaceAll(
						"\\s*", "");
				if (Funksjon.sjekkIder(inn)) {
					ansatte = AnsattListe.utvidMedIder(ansatte, inn);
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
		} else if (!er_mote) {
			if (avtaleId > 0) {
				System.out.println("Gammelt sted: " + avt.getSted());
			}
			System.out.println("Sted (hva som helst): ");
			sted = this.ventStdInn(true);
		}
		if (er_mote) {
			if (ansatte == null) {
				ansatte = avt.getDeltakere();
			}
			System.out.println("Hvilket rom med minimum " + ansatte.size()
					+ " i kapasitet?");
			GeneriskVisning.printRom(RomListe.ledigeMedMinimumKapasitet(ansatte
					.size(),  start, slutt));
			do {
				inn = this.ventStdInn();
				try {
					romId = Integer.parseInt(inn);
					break;
				} catch (NumberFormatException u) {
					continue;
				}
			} while (false);
		}
		else {
			ansatte = new ArrayList<Ansatt>();
			ansatte.add(Ansatt.medId(ansattId));
		}
		if (avt == null) {
			avt = Avtale.medId(Database.nyRad("avtale"));
		}
		this.oppdaterAvtale(avt, navn, beskrivelse, start, slutt, sted, romId,
				ansattId, ansatte);
		new KalenderKontroller();
		return;
	}

	private void oppdaterAvtale(Avtale avt, String navn, String beskrivelse,
			Date start, Date slutt, String sted, int romId, int ansattId,
			ArrayList<Ansatt> ansatte) throws FileNotFoundException,
			SQLException, IOException, Exception {
		avt.setNavn(navn);
		avt.setBeskrivelse(beskrivelse);
		avt.setStart(start);
		avt.setSlutt(slutt);
		avt.setSted(sted);
		avt.setRomId(romId);
		avt.oppdater();
		if (ansatte == null) {
			// avtalen/møtet fins allerede, og det skal ikke legges til noen nye deltakere
			Connection kobling = Database.getInstans().getKobling();
			Statement beretning = kobling.createStatement();
			String sql = "update ansatt_avtale set status_id = 5 where status_id != 1 and avtale_id = " + avt.getId() + ";";
			beretning.executeUpdate(sql);
		}
		else {
			// avtalen/møtet er nytt, og deltakere må legges til
			Connection kobling = Database.getInstans().getKobling();
			Statement beretning;
			String sql;
			for (int i = 0; i < ansatte.size(); i++) {
				beretning = kobling.createStatement();
				sql = "insert into ansatt_avtale (ansatt_id,avtale_id,status_id) values(" + ansatte.get(i).getId() + "," + avt.getId() + "," + (ansattId == ansatte.get(i).getId() ? "1" : "2") + ");";
				beretning.executeUpdate(sql);
			}
		}
	}

	private void visAvtale(int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = Avtale.medId(avtaleId);
		int ansattId = KontrollerData.getInstans().getInnlogga().getId();
		if (avt == null) {
			System.out.println("Valgt avtale fins ikke.");
			new KalenderKontroller();
			return;
		}
		AvtaleVisning.visAvtale(avt);
		if (avt.erDeltakerMedId(ansattId)) {
			GeneriskVisning.printKommando("v", "alarm");
		}
		if (ansattId == avt.getMotelederId()) {
			GeneriskVisning.printKommando("e", "endre");
			GeneriskVisning.printKommando("s", "slett");
		}
		else if (avt.getAktiv()) {
			GeneriskVisning.printKommando("i", "svar på/endre invitasjon");
		}
		GeneriskVisning.printKommando("k", "kalender");
		GeneriskVisning.printKommando("q", "avslutt");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'v':
				if (avt.erDeltakerMedId(ansattId)) {
					new AlarmKontroller(ansattId, avtaleId);
					return;
				}
				break;
			case 'e':
				if (ansattId == avt.getMotelederId()) {
					this.endreAvtale(avtaleId);
					return;
				}
				break;
			case 's':
				if (ansattId == avt.getMotelederId()) {
					this.slettAvtale(avtaleId);
					return;
				}
				break;
			case 'i':
				if (ansattId != avt.getMotelederId()) {
					this.endreInvitasjon(avtaleId);
					return;
				}
				break;
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

	private void endreInvitasjon (int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		int ansattId = KontrollerData.getInstans().getInnlogga().getId();
		Avtale avt = Avtale.medId(avtaleId);
		if (avt == null) {
			System.out.println("Valgt avtale/møte fins ikke.");
			return;
		}
		if (avt.getDeltakere().size() == 1) {
			System.out.println("Dette er en avtale og ikke et møte.");
			return;
		}
		Status denne = avt.getStatusMedAnsattId(ansattId);
		if (denne.getId() == 6) {
			System.out.println("Møtet er avlyst.");
			return;
		}
		if (ansattId == avt.getMotelederId()) {
			System.out.println("Du er møteleder.");
			return;
		}
		System.out.println("Nåværende status: " + denne.getNavn());
		System.out.println("Endre til:\n");
		if (denne.getId() == 2 || denne.getId() == 4 || denne.getId() == 5) {
			GeneriskVisning.printKommando("3", Status.medId(3).getNavn());
		}
		if (denne.getId() == 2 || denne.getId() == 3 || denne.getId() == 5) {
			GeneriskVisning.printKommando("4", Status.medId(4).getNavn());
		}
		GeneriskVisning.printKommando("a", "avbryt");
		String inn;
		int nyStatusId = 2;
		do {
			switch (ventStdInn().charAt(0)) {
			case '3':
				if (denne.getId() == 2 || denne.getId() == 4 || denne.getId() == 5) {
					nyStatusId = 3;
					break;
				}
				continue;
			case '4':
				if (denne.getId() == 2 || denne.getId() == 3 || denne.getId() == 5) {
					nyStatusId = 4;
					break;
				}
				continue;
			case 'a':
				this.visAvtale(avtaleId);
				return;
			}
		} while(false);
		avt.setStatusIdMedAnsattId(nyStatusId , ansattId);
		System.out.println("Invitasjonen er besvart. Trykk linjeskift...");
		this.ventStdInn(true);
		new KalenderKontroller();
		return;
	}

	private void slettAvtale(int avtaleId) throws Exception {
		GeneriskVisning.printTopp();
		Avtale avt = Avtale.medId(avtaleId);
		if (avt == null) {
			System.out.println("Valgt avtale fins ikke.");
			return;
		}
		if (KontrollerData.getInstans().getInnlogga().getId() != avt.getMotelederId()) {
			System.out.println("Du har ikke adgang.");
			return;
		}
		System.out.println("Slette avtale \"" + avt.getNavn() + "\"? (y/*)");
		if (!ventStdInn(true).equals("y")) {
			this.visAvtale(avtaleId);
			return;
		}
		Connection kobling = Database.getInstans().getKobling();
		Statement beretning = kobling.createStatement();
		String sql = "update avtale set aktiv=0 where id=" + avtaleId + ";";
		beretning.executeUpdate(sql);
		System.out.println("Avtale er sletta. Trykk linjeskift...");
		this.ventStdInn(true);
		new KalenderKontroller();
		return;
	}
}
