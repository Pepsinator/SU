package visning;

import java.sql.SQLException;
import java.util.ArrayList;

import bibliotek.Funksjon;


import modell.Ansatt;
import modell.Gruppe;
import modell.GruppeListe;

public class GeneriskVisning {
	public static void printTopp() {
		System.out.println(Funksjon.strRepeat("\n" , 100));
	}

	public static void printKommando (String id , String navn) {
		System.out.println(id + Funksjon.strRepeat(" " , 5 - id.length()) + navn);
	}

	public static void printAnsatte (ArrayList<Ansatt> ansatte) {
		for (int i = 0; i < ansatte.size(); i++) {
			GeneriskVisning.printKommando("" + ansatte.get(i).getId() , ansatte.get(i).getNavn());
		}
	}

	public static void printGrupper() throws SQLException {
		GeneriskVisning.printGrupper(0, 1);
	}

	public static void printGrupper(int rotId) throws SQLException {
		GeneriskVisning.printGrupper(rotId, 1);
	}

	public static void printGrupper(int rotId, int tab) throws SQLException {
		Gruppe rot = Gruppe.medId(rotId);
		if (rot != null) {
			System.out.print(Funksjon.strRepeat("\t" , tab));
			GeneriskVisning.printKommando("" + rot.getId() , rot.getNavn());
		}
		ArrayList<Gruppe> grl = GruppeListe.medGruppeId(rot.getId());
		for (int i = 0; i < grl.size(); i++) {
			GeneriskVisning.printGrupper(grl.get(i).getId(), tab + 1);
		}
	}
}
