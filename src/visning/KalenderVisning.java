package visning;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import modell.Ansatt;
import modell.AvtaleListe;
import modell.Gruppe;

public class KalenderVisning {
	@SuppressWarnings("deprecation")
	public static void print(Date tid, Ansatt ans , Gruppe grp) throws SQLException {
		GeneriskVisning.printTopp();
		System.out.println("==Kalendervisning==\n");
		Calendar kal = Calendar.getInstance();
		System.out.print("Uke: " + (new SimpleDateFormat("ww")).format(tid)
				+ "/" + (new SimpleDateFormat("YYYY")).format(tid));
		if (ans != null) {
			System.out.print("     Ansatt: " + ans.getNavn());
		}
		if (grp != null) {
			System.out.print("     Gruppe:" + grp.getNavn());
		}
		String[] dager = { "Ma", "Ti", "On", "To", "Fr", "Lø", "Sø" };
		System.out.print("\n\n        ");
		for (int i = 0; i < dager.length; i++) {
			System.out.print(dager[i] + "       ");
		}
		System.out.println();
		System.out.print("        ");
		Date[] dag = new Date[7];
		for (int i = 0; i < dager.length; i++) {
			dag[i] = new Date((new Date((new SimpleDateFormat("YYYY/MM/dd")).format(tid))).getTime() + 86400000 * (2 + i - kal.get(Calendar.DAY_OF_WEEK)));
			System.out
					.print((new SimpleDateFormat("dd/MM")).format(dag[i]) + "    ");
		}
		System.out.println();
		for (int i = 0; i < 24; i++) {
			System.out.print(i < 10 ? "0" : "");
			System.out.print(i);
			System.out.print("-");
			System.out.print(i + 1 < 10 ? "0" : "");
			System.out.print(i + 1);
			System.out.print("   ");
			String ider;
			for (int j = 0; j < dager.length; j++) {
				ider = AvtaleListe.oppramsIder(AvtaleListe.medTidsrom(new Date(dag[j].getTime() + i * 3600000), new Date(dag[j].getTime() + (i + 1) * 3600000)));
				System.out.print(ider + new String(new char[9 - ider.length()]).replace("\0", " "));
			}
			System.out.println();
		}
	}
}
