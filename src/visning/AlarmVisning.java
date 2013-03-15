package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Funksjon;

import modell.Alarm;
import modell.AlarmListe;
import modell.Avtale;
import modell.KontrollerData;

public class AlarmVisning {

	/**
	 * Kan Martin ordne med printingen her? :)
	 */
	
	//får inn array med alle alarmene og printer dem ut
	public static void printAlarmer() throws FileNotFoundException, SQLException, IOException{
		System.out.println("Alarmer:\n");
		printAlarmer(false);
	}
	public static void printAlarmer(boolean ramme) throws FileNotFoundException, SQLException, IOException{
		ArrayList<Alarm> alarmer = AlarmListe.medAnsattId(KontrollerData.getInstans().getInnlogga().getId());
		if (ramme) {
			if (alarmer.size() == 0) {
				return;
			}
			int kolStrl = 50;
			System.out.println(" +" + Funksjon.strRepeat("-", kolStrl + 2) + "+ ");
			String str;
			for (int i = 0; i < alarmer.size(); i++) {
				str = alarmer.get(i).getAvtale().getNavn() + ": " + Funksjon.sekTilTid(alarmer.get(i).getTidForAvtale());
				System.out.println(" | " + str + Funksjon.strRepeat(" ", kolStrl - str.length()) + " | ");
			}
			System.out.println(" +" + Funksjon.strRepeat("-", kolStrl + 2) + "+ ");
			System.out.println();
			return;
		}
		System.out.println("Alarm ID\t\t Avtalenavn \t\t\tTid Før avtale");
		for(int i = 0; i < alarmer.size(); i++){
			formatAlarm(alarmer.get(i));
		}
		System.out.println("");
	}
	
	public static void formatAlarm(Alarm alarm) throws FileNotFoundException, SQLException, IOException{
		
		System.out.println("" + alarm.getId() + "\t\t\t"
				+ Avtale.medId(alarm.getAvtaleId()).getNavn() + "\t\t\t"  // henter avtalenavnet
				+ Funksjon.sekTilTid(alarm.getTidForAvtale()));
	}
	
	public static void visAlarm(Alarm alarm) throws FileNotFoundException, SQLException, IOException{
		System.out.println("Alarm:\n");
		System.out.println("ID:\t\t" + alarm.getId());
		System.out.println("Avtale:\t\t" + Avtale.medId(alarm.getAvtaleId()).getNavn());
		System.out.println("Tid før avtale:\t" + Funksjon.sekTilTid(alarm.getTidForAvtale()));
	}
	
	
}
