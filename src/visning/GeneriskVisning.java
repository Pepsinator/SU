package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import bibliotek.Funksjon;


import modell.Ansatt;
import modell.Gruppe;
import modell.GruppeListe;
import modell.KontrollerData;
import modell.Rom;

public class GeneriskVisning {
	public static void printTopp() throws SQLException, FileNotFoundException, IOException {
		System.out.println(Funksjon.strRepeat("\n" , 100));
		System.out.println("Logga inn som: " + KontrollerData.getInstans().getInnlogga().getNavn() + "\n");
		AlarmVisning.printAlarmer(true);
	}

	public static void printKommando (String id , String navn) {
		System.out.println(id + Funksjon.strRepeat(" " , 7 - id.length()) + navn);
	}

	public static void printAnsatteMote (ArrayList<Ansatt> ansatte, modell.Avtale avt) throws SQLException, IOException {
		for (int i = 0; i < ansatte.size(); i++) {
			String status = "";												//Skriver ut statusen for avtalen i avtaleVisning
			int s = avt.getStatusIdMedAnsattId(ansatte.get(i).getId());
			int lengde = ansatte.get(i).getNavn().length();
			status = Funksjon.strRepeat(" " , 18-lengde);
			switch(s){
			case(1): status += "(Leder)";
			break;
			case(2): status += "(Venter p� svar)";
			break;
			case(3): status += "(Godtatt)";
			break;
			case(4): status += "(Avsl�tt)";
			break;
			case(5): status += "(Venter p� svar)";
			break;
			}
			GeneriskVisning.printKommando("" + ansatte.get(i).getId() , ansatte.get(i).getNavn() + status);
		}
	}
	
	public static void printAnsatte (ArrayList<Ansatt> ansatte) {
		for (int i = 0; i < ansatte.size(); i++) {
			GeneriskVisning.printKommando("" + ansatte.get(i).getId() , ansatte.get(i).getNavn());
		}
	}

	public static void printGrupper() throws SQLException, FileNotFoundException, IOException {
		GeneriskVisning.printGrupper(0, 0);
	}

	public static void printGrupper(int rotId) throws SQLException, FileNotFoundException, IOException {
		GeneriskVisning.printGrupper(rotId, 0);
	}

	public static void printGrupper(int rotId, int tab) throws SQLException, FileNotFoundException, IOException {
		ArrayList<Gruppe> grl = GruppeListe.medRotId(rotId);
		for (int i = 0; i < grl.size(); i++) {
			GeneriskVisning.printKommando("" + grl.get(i).getId() , Funksjon.strRepeat(" " , tab * 4) + grl.get(i).getNavn());
			GeneriskVisning.printGrupper(grl.get(i).getId(), tab + 1);
		}
	}
	
	public static void printRom (ArrayList<Rom> rom) {
		for (int i = 0; i < rom.size(); i++) {
			GeneriskVisning.printKommando("" + rom.get(i).getId() , rom.get(i).getNavn() + "(maks " + rom.get(i).getKapasitet() + ")");
		}
	}
}
