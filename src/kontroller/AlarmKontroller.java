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
		visValgtAlarm(Alarm.medAnsattIdAvtaleId(ansattId, avtaleId));
	}
	
	public void visAlarmer() throws Exception{
		GeneriskVisning.printTopp();
		AlarmVisning.printAlarmer();
		
		GeneriskVisning.printKommando("k", "kalender");
		GeneriskVisning.printKommando("n", "nytt varsel");
		
		String inn = ventStdInn();
		
		//Hvis brukeren gir en alarmid skal alarminfo vises
		do {
			int avtId;
			try {
				avtId = Integer.parseInt(inn);
			}
			catch (NumberFormatException u) {
				break;
			}
			new AlarmKontroller(avtId);
			return;
		} while (false);
		
		switch(inn.charAt(0)){
		case('t'):
			new KalenderKontroller();
		case('n'):
			visAvtalerUtenAlarm();
		}
	}
	
	//Viser egenskapene til en valgt alarm
	public void visValgtAlarm(Alarm alarm) throws Exception{
		GeneriskVisning.printTopp();
		if (alarm == null) {
			System.out.println("Du har ikke lagt til noen alarm for denne avtalen ennå.");
		}
		else {
			AlarmVisning.visAlarm(alarm);
		}
		System.out.println();
		GeneriskVisning.printKommando("e", "endre");
		GeneriskVisning.printKommando("s", "slette");
		GeneriskVisning.printKommando("a", "vis avtalen");
		GeneriskVisning.printKommando("k", "vis kalender");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'e':
				//this.endreAlarm(alarm.getId());
				return;
			case 's':
				alarm.slett();
				new AlarmKontroller();
				return;
			case 'a':
				new AvtaleKontroller(alarm.getAvtaleId());
				return;
			case 'k':
				new KalenderKontroller();
				return;
			default:
				continue;
			}
		} while (false);
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
