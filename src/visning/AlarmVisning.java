package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import modell.Alarm;
import modell.AlarmListe;
import modell.Avtale;

public class AlarmVisning {

	
	//f�r inn array med alle alarmene og printer dem ut
	public static String printAlarmer() throws FileNotFoundException, SQLException, IOException{
		ArrayList<Alarm> alarmer = AlarmListe.alle();
		System.out.println("Avtale ID\t\t Avtalenavn \t\t\tTid F�r avtale");
		for(int i = 0; i < alarmer.size(); i++){
			formatAlarm(alarmer.get(i));
		}
		System.out.println("");
		return null;
		
	}
	
	public static void formatAlarm(Alarm alarm) throws FileNotFoundException, SQLException, IOException{
		
		System.out.println("" + alarm.getAvtale_id() + "\t\t\t"
				+ Avtale.medId(alarm.getAvtale_id()).getNavn() + "\t\t\t"  // henter avtalenavnet
				+ alarm.getTidForAvtale());
	}
	
}
