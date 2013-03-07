package bibliotek;

public class Funksjon {
	public static String strRepeat (String str , int ant) {
		return new String(new char[ant]).replace("\0", str);
	}
}
