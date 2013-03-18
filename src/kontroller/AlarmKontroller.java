package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Database;
import bibliotek.Funksjon;

import visning.AlarmVisning;
import visning.AvtaleVisning;
import visning.GeneriskVisning;
import modell.Alarm;
import modell.Avtale;
import modell.AvtaleListe;

public class AlarmKontroller extends AbstraktKontroller {
	
	public AlarmKontroller() throws Exception{
		super();
		visAlarmer();
	}
	
	public AlarmKontroller(int alarmId) throws Exception{
		super();
		visValgtAlarm(Alarm.medId(alarmId));
	}
	
	public AlarmKontroller(int ansattId, int avtaleId) throws Exception{
		super();
		visValgtAlarm(Alarm.medAnsattIdAvtaleId(ansattId, avtaleId), avtaleId);
	}
	
	//Printer ut alarmer
	public void visAlarmer() throws Exception{
		GeneriskVisning.printTopp();
		AlarmVisning.printAlarmer();
		
		GeneriskVisning.printKommando("k", "kalender");
		GeneriskVisning.printKommando("n", "nytt varsel");
		GeneriskVisning.printKommando("q", "avslutt");
		
		String inn = ventStdInn();
		
		//Hvis brukeren gir en alarmid skal alarminfo vises
		do {
			int alarmId;
			try {
				alarmId = Integer.parseInt(inn);
			}
			catch (NumberFormatException u) {
				break;
			}
			new AlarmKontroller(alarmId);
			return;
		} while (false);
		
		switch(inn.charAt(0)){
		case('k'):
			new KalenderKontroller();
		case('n'):
			visAvtalerUtenAlarm();
		case('q'):
			this.avslutt();
		default:
			new AlarmKontroller();
			System.out.println("Ugyldig kommando");
		}
	}
	
	//Viser egenskapene til en valgt alarm
	public void visValgtAlarm(Alarm alarm) throws Exception{
		GeneriskVisning.printTopp();
		AlarmVisning.visAlarm(alarm);
		System.out.println();
		GeneriskVisning.printKommando("e", "endre");
		GeneriskVisning.printKommando("s", "slett");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'e':
				endreAlarm(alarm.getId());
				return;
			case 's':
				alarm.slett();
				new AlarmKontroller();
				return;
			default:
				break;
			}
		} while (true);
	}

	//Viser egenskapene til en valgt alarm (tar med avtaleId i tilfelle avtalen ikke finnes)
	public void visValgtAlarm(Alarm alarm, int avtaleId) throws Exception{
		GeneriskVisning.printTopp();
		if (alarm == null) {
			System.out.println("Du har ikke lagt til noen alarm for denne avtalen ennå.\n");
			GeneriskVisning.printKommando("n", "legg til ny alarm");
			do {
				switch (this.ventStdInn().charAt(0)) {
				case 'n':
					//lagNyAlarm må ha en liste med avtaler som ikke har alarmer
					//i dette tilfellet vet vi at avtalen ikke har alarm, så vi
					//lager bare en liste med den avtalen som skal få ny alarm
					ArrayList<Avtale> avt = new ArrayList<Avtale>();
					avt.add(Avtale.medId(avtaleId));
					
					lagNyAlarm(avtaleId, avt);
					return;
				default:
					break;
				}
			} while (true);
		}
		else {
			AlarmVisning.visAlarm(alarm);
			System.out.println();
			GeneriskVisning.printKommando("e", "endre");
			GeneriskVisning.printKommando("s", "slett");
			do {
				switch (this.ventStdInn().charAt(0)) {
				case 'e':
					endreAlarm(alarm.getId());
					return;
				case 's':
					alarm.slett();
					new AlarmKontroller();
					return;
				default:
					break;
				}
			} while (true);
		}
	}
	
	//Endre en gitt alarm
	private void endreAlarm(int alarmId) throws Exception {
		Alarm alarm = Alarm.medId(alarmId);
		int sek = 0;
		int avtaleId = alarm.getAvtale().getId();
		
		System.out.println("Skriv inn ny tid før avtale (tt:mm:ss): ");
		do{
			String inn = this.ventStdInn();
			if(!Funksjon.sjekkTidsFormat(inn)) {
					System.out.println("Feil tidsformat");
					continue;
			}
			sek = Funksjon.tidTilSek(inn);
		}while(false);
		alarm.oppdater(sek, avtaleId);
		new AlarmKontroller();
	}

	//Viser avtaler som ikke har en alarm
	public void visAvtalerUtenAlarm() throws Exception{
		String sql = "select avtale.id from avtale where avtale.id NOT IN" +
				"(select avtale.id from avtale, alarm where avtale_id = avtale.id)";
		ArrayList<Avtale> avt = AvtaleListe.medSql(sql);
		GeneriskVisning.printTopp();
		AvtaleVisning.visAvtaler(avt);
		System.out.println("Velg id for å legge til alarm:");
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
			lagNyAlarm(avtaleId, avt);
			return;
		} while (false);
	}
	
	//Legg til en ny alarm til en avtale
	public void lagNyAlarm(int avtaleId, ArrayList<Avtale> avtList ) throws Exception{
		Avtale avt = Avtale.medId(avtaleId);
		//Sjekker om avtalen ikke finnes eller allerede har alarm
		if(avt == null ){
			System.out.println("Avtalen finnes ikke");
		}else if(avtList.contains(avt)){
			System.out.println("Avtalen har allerede alarm");
		}else{
			//Tar inn en tid, looper helt til en gyldig tid blir valgt
			int sek = 0;
			System.out.println("Skriv inn tid før avtale (tt:mm:ss): ");
			do{
				String inn = this.ventStdInn();
				if(!Funksjon.sjekkTidsFormat(inn)) {
					System.out.println("Feil tidsformat");
					continue;
				}
				sek = Funksjon.tidTilSek(inn);
			}while(false);
			//legger til alarm i databasen
			Alarm alarm = Alarm.medId(Database.nyRad("alarm"));
			alarm.oppdater(sek, avtaleId);
			new AlarmKontroller();
		}	
	}
}