package bibliotek;

import java.util.regex.Pattern;

public class Funksjon {
	public static String strRepeat (String str , int ant) {
		return new String(new char[ant]).replace("\0", str);
	}
	public static boolean sjekkIder (String str) {
		return Pattern.matches("^[0-9]+(,[0-9]+)*$" , str);
	}
}
