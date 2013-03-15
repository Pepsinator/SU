package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import visning.AlarmVisning;
import visning.AvtaleVisning;
import visning.GeneriskVisning;
import modell.Alarm;
import modell.AlarmListe;
import modell.Avtale;
import modell.AvtaleListe;

public class AlarmKontroller extends AbstraktKontroller {
	
	public AlarmKontroller() throws Exception{
		super();
		visAlarmer();
	}
	
	public AlarmKontroller(int alarmId) throws SQLException, FileNotFoundException, IOException{
		super();
		visValgtAlarm(Alarm.medId(alarmId));
	}
	
	public AlarmKontroller(int ansattId, int avtaleId) throws SQLException, FileNotFoundException, IOException{
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
		case('t'):
			new KalenderKontroller();
		case('n'):
			visAvtalerUtenAlarm();
		}
	}
	
	public void visValgtAlarm(Alarm alarm) throws SQLException, FileNotFoundException, IOException{
		GeneriskVisning.printTopp();
		if (alarm == null) {
			System.out.println("Du har ikke lagt til noen alarm for denne avtalen ennå.");
		}
		else {
			AlarmVisning.visAlarm(alarm);
		}
		System.out.println();
		GeneriskVisning.printKommando("e", "endre");
		do {
			switch (this.ventStdInn().charAt(0)) {
			case 'e':
				//this.endreAlarm(alarm.getId());
				return;
			default:
				break;
			}
		} while (true);
	}

	public void visAvtalerUtenAlarm() throws FileNotFoundException, SQLException, IOException{
		String sql = "select avtale.id from avtale where avtale.id NOT IN" +
				"(select avtale.id from avtale, alarm where avtale_id = avtale.id)";
		ArrayList<Avtale> avt = AvtaleListe.medSql(sql);
//		System.out.println(Avtaler);
		GeneriskVisning.printTopp();
		AvtaleVisning.visAvtaler(avt);
	}
}
