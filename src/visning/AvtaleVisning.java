package visning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import modell.Ansatt;
import modell.AnsattListe;
import modell.Avtale;
import modell.Mote;

public class AvtaleVisning {
	public static void visAvtale (Avtale avt) throws SQLException, FileNotFoundException, IOException {
		System.out.println("==Avtaleinfo==\n");
		System.out.println("Navn:      " + avt.getNavn());
		System.out.println("Start:     " + avt.getStart());
		System.out.println("Slutt:     " + avt.getSlutt());
		System.out.println();
		Mote m = avt.getMote();
		if (m != null) {
			System.out.println("Møteleder: " + m.getAnsatt().getNavn());
			System.out.println("\nDeltakere:");
			ArrayList<Ansatt> ansatte = AnsattListe.medMoteId(m.getId());
			if (ansatte.size() > 0) {
				GeneriskVisning.printAnsatte(ansatte);
			}
			else {
				System.out.println("\tIngen deltakere.");
			}
			System.out.println();
		}
	}
}
