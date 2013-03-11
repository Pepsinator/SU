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
		ArrayList<Ansatt> deltakere = AnsattListe.medAvtaleId(avt.getId());
		if (deltakere.size() > 0) {
			System.out.println("Møteleder: " + avt.getMoteLeder().getNavn());
			System.out.println("\nDeltakere:");
			GeneriskVisning.printAnsatte(deltakere);
			System.out.println();
		}
	}
}
