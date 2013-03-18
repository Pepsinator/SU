package bibliotek;

import java.util.regex.Pattern;

public class Funksjon {
	public static String strRepeat (String str , int ant) {
		return new String(new char[ant]).replace("\0", str);
	}
	
	public static boolean sjekkIder (String str) {
		return Pattern.matches("^[0-9]+(,[0-9]+)*$" , str);
	}
	
	// regner om fra sekunder til tid på formatet tt:mm:ss
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
	
	//sjekker om en string er på formatet tt:mm:ss
	public static Boolean sjekkTidsFormat(String format){
		if(format.length() > 8){
			return false;
		}
		if(format.charAt(2) != ':' && format.charAt(4) != ':'){
			return false;
		}
		int[] sifferIndekser = {0, 1, 3, 4, 6, 7};
		for(int i : sifferIndekser){
			if((!Character.isDigit(format.charAt(i)))){
				return false;
			}
		}
		return true;
	}
	
	// omformaterer fra tt:mm:ss til sekunder
	public static int tidTilSek(String tid){
		int sek = Integer.parseInt((String) tid.subSequence(6, 8));
		int minutter = Integer.parseInt((String) tid.subSequence(3, 5));
		int timer = Integer.parseInt((String) tid.subSequence(0, 2));
		return ((timer * 3600) + sek + (minutter*60));
	}
}
