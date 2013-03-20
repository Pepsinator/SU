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
	
	//får inn array med alle alarmene og printer dem ut
	public static void printAlarmer() throws FileNotFoundException, SQLException, IOException{
		System.out.println("Alarmer:\n");
		printAlarmer(false);
	}
	
	//printer alarmer, enten med eller uten ramme rundt
	public static void printAlarmer(boolean ramme) throws FileNotFoundException, SQLException, IOException{
		ArrayList<Alarm> alarmer;
		if (ramme) {
			alarmer = AlarmListe.aktuelleMedAnsattId(KontrollerData.getInstans().getInnlogga().getId());
			if (alarmer.size() == 0) {
				return;
			}
			int radStrl = 80;
			int kolStrl = 50;
			System.out.println(" +" + Funksjon.strRepeat("-", radStrl + 2) + "+ ");
			String str;
			int sek;
			for (int i = 0; i < alarmer.size(); i++) {
				sek = alarmer.get(i).getSekTilStart();
				str = alarmer.get(i).getAvtale().getId() + "";
				str += Funksjon.strRepeat(" ", 6 - str.length());
				str += alarmer.get(i).getAvtale().getNavn() + ":";
				str += Funksjon.strRepeat(" ", kolStrl - str.length());
				if (sek > 0) {
					str += "kun " + Funksjon.sekTilTid(sek) + " til avtale";
				}
				else {
					str += Funksjon.sekTilTid(-sek) + " siden avtalestart";
				}
				str += Funksjon.strRepeat(" ", radStrl - str.length());
				System.out.println(" | " + str + " | ");
			}
			System.out.println(" +" + Funksjon.strRepeat("-", radStrl + 2) + "+ ");
			System.out.println();
			return;
		}
		//Vanlig visning av alarmer (ramme = false)
		alarmer = AlarmListe.medAnsattId(KontrollerData.getInstans().getInnlogga().getId());
		System.out.println("Alarm ID   Avtalenavn               Tid Før avtale");
		for(int i = 0; i < alarmer.size(); i++){
			formatAlarm(alarmer.get(i));
		}
		System.out.println("");
	}
	
	//hjelpemetode til printing av alarmer
	public static void formatAlarm(Alarm alarm) throws FileNotFoundException, SQLException, IOException{
		String avtIdStr = String.valueOf(alarm.getId());					//Alarm ID
		String avtNavnStr = alarm.getAvtale().getNavn();					//Avtalenavn
		String avtTidForStr = Funksjon.sekTilTid(alarm.getTidForAvtale());	//Tid før avtale
		
		
		System.out.println(avtIdStr + Funksjon.strRepeat(" ", 11 - avtIdStr.length())
				+ avtNavnStr + Funksjon.strRepeat(" ", 25 - avtNavnStr.length())
				+ avtTidForStr);
	}
	
	//viser én alarm for seg selv
	public static void visAlarm(Alarm alarm) throws FileNotFoundException, SQLException, IOException{
		System.out.println("Alarm:\n");
		System.out.println("ID:\t\t" + alarm.getId());
		System.out.println("Avtale:\t\t" + Avtale.medId(alarm.getAvtaleId()).getNavn());
		System.out.println("Tid før avtale:\t" + Funksjon.sekTilTid(alarm.getTidForAvtale()));
	}
	
}
