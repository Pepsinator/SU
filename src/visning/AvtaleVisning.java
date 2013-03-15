package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.KontrollerData;
import modell.Rom;

public class AvtaleVisning {
	public static void visAvtale (Avtale avt) throws SQLException, FileNotFoundException, IOException {
		int dinId = KontrollerData.getInstans().getInnlogga().getId();
		System.out.println("==Avtaleinfo==\n");
		System.out.println("Navn:          " + avt.getNavn());
		System.out.println("Start:         " + avt.getStart());
		System.out.println("Slutt:         " + avt.getSlutt());
		System.out.println("Beskrivelse:   " + avt.getBeskrivelse());
		ArrayList<Ansatt> deltakere = AnsattListe.medAvtaleId(avt.getId());
		if (deltakere.size() > 1) {
			System.out.println();
			if (avt.getMotelederId() != dinId){//Kun andre deltagere får oppgitt møteleder her
				System.out.println("Møteleder: " + avt.getMoteleder().getNavn());
			}
			Rom rom = avt.getRom();
			if (rom != null) {
				System.out.println("Rom:           " + avt.getRom().getNavn());
			}
			System.out.println("Din status for avtalen: " + modell.Melding.VisMelding(avt, dinId));//Skriver ut status i avtaleVisning
			System.out.println("\nDeltakere:");
			GeneriskVisning.printAnsatteMote(deltakere, avt);
			System.out.println();
		}
		else {
			System.out.println("Sted:          " + avt.getSted());
		}
	}
	public static void visAvtaler(ArrayList<Avtale> avtaler){
		System.out.println("Avtaler: ");
		System.out.println("ID:" + "\t" + "Navn:");
		
		for(int i = 0; i < avtaler.size() ; i++){
//			if avtaler.get(i).getAktiv(){
				System.out.println(avtaler.get(i).getId() + "\t" + avtaler.get(i).getNavn());
//			}
		}
		System.out.println("");
		
	}
}
