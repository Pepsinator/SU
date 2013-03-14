package kontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import visning.AlarmVisning;
import visning.GeneriskVisning;
import modell.Avtale;

public class AlarmKontroller extends AbstraktKontroller {
	
	public AlarmKontroller() throws Exception{
		super();
		visAlarmer();
	}
	
	public AlarmKontroller(int avtaleId) throws SQLException, FileNotFoundException, IOException{
		super();
		visValgtAlarm(Avtale.medId(avtaleId));
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
	
	public void visValgtAlarm(Avtale avtale) throws SQLException{
		GeneriskVisning.printTopp();
	}

}
