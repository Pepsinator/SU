package bibliotek;

import java.util.regex.Pattern;

public class Funksjon {
	public static String strRepeat (String str , int ant) {
		return new String(new char[ant]).replace("\0", str);
	}
	public static boolean sjekkIder (String str) {
		return Pattern.matches("^[0-9]+(,[0-9]+)*$" , str);
	}
	public static String sekTilTid(int sek){
		
		int timer;
		int minutter;
		int sekunder;
		
		timer = 	sek/3600;
		minutter= 	(sek%3600)/60;
		sekunder = 	sek - minutter*60 - timer*3600;
		
		String tid = (timer <= 9 ? "0":"") + timer + ":" + 
				(minutter <= 9 ? "0":"") + minutter + ":" + 
				(sekunder <= 9 ? "0":"") + sekunder;
		
		return(tid);
	}
}
