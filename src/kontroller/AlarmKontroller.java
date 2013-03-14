package kontroller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import visning.AlarmVisning;
import visning.GeneriskVisning;
import modell.Alarm;
import modell.Avtale;

public class AlarmKontroller extends AbstraktKontroller {
	
	public AlarmKontroller() throws Exception{
		super();
		visAlarmer();
	}
	
	public AlarmKontroller(Avtale avtale) throws SQLException{
		super();
		visValgtAlarm(avtale);
	}
	
	public void visAlarmer() throws Exception{
		GeneriskVisning.printTopp();
		System.out.println("Alattrmer:\n");
		AlarmVisning.printAlarmer();
		GeneriskVisning.printKommando("t", "tilbake");
		GeneriskVisning.printKommando("n", "nytt varsel");
		String inn = ventStdInn();
	
		switch(inn.charAt(0)){
		case('t'):
			new KalenderKontroller();
		case('n'):
			//Nytt varsel
		}
	}
	
	public ArrayList<Alarm> hentAlarmer(){
		
		return null;
	}
	
	public void visValgtAlarm(Avtale avtale) throws SQLException{
		GeneriskVisning.printTopp();
	}

}
