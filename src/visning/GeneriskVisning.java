package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import kontroller.KontrollerData;

import bibliotek.Funksjon;


import modell.Ansatt;
import modell.Gruppe;
import modell.GruppeListe;

public class GeneriskVisning {
	public static void printTopp() throws SQLException {
		System.out.println(Funksjon.strRepeat("\n" , 100) + "Logga inn som: " + KontrollerData.getInstans().getInnlogga().getNavn() + "\n");
	}

	public static void printKommando (String id , String navn) {
		System.out.println(id + Funksjon.strRepeat(" " , 7 - id.length()) + navn);
	}

	public static void printAnsatte (ArrayList<Ansatt> ansatte) {
		for (int i = 0; i < ansatte.size(); i++) {
			GeneriskVisning.printKommando("" + ansatte.get(i).getId() , ansatte.get(i).getNavn());
		}
	}

	public static void printGrupper() throws SQLException, FileNotFoundException, IOException {
		GeneriskVisning.printGrupper(0, 1);
	}

	public static void printGrupper(int rotId) throws SQLException, FileNotFoundException, IOException {
		GeneriskVisning.printGrupper(rotId, 1);
	}

	public static void printGrupper(int rotId, int tab) throws SQLException, FileNotFoundException, IOException {
		Gruppe rot = Gruppe.medId(rotId);
		if (rot != null) {
			System.out.print(Funksjon.strRepeat("\t" , tab));
			GeneriskVisning.printKommando("" + rot.getId() , rot.getNavn());
		}
		ArrayList<Gruppe> grl = GruppeListe.medRotId(rot.getId());
		for (int i = 0; i < grl.size(); i++) {
			GeneriskVisning.printGrupper(grl.get(i).getId(), tab + 1);
		}
	}
}
