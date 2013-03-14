package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import visning.AlarmVisning;
import visning.GeneriskVisning;
import modell.Alarm;
import modell.AlarmListe;
import modell.Avtale;

public class AlarmKontroller extends AbstraktKontroller {
	
	public AlarmKontroller() throws Exception{
		super();
		visAlarmer();
	}
	
	public AlarmKontroller(int alarmId) throws SQLException, FileNotFoundException, IOException{
		super();
		visValgtAlarm(Alarm.medId(alarmId));
	}
	
	public void visAlarmer() throws Exception{
		GeneriskVisning.printTopp();
		AlarmVisning.printAlarmer();
		
		GeneriskVisning.printKommando("t", "tilbake");
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
			//Nytt varsel
		}
	}
	
	public void visValgtAlarm(Alarm alarm) throws SQLException{
		GeneriskVisning.printTopp();
		AlarmVisning.visAlarm(alarm);
	}

}
