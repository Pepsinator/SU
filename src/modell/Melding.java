package modell;

import java.io.IOException;
import java.sql.SQLException;

public class Melding {
	
	public static String VisMelding(Avtale avt,int ansattId) throws SQLException, IOException{;
		String str = null;
		switch (avt.getStatusIdMedAnsattId(ansattId)){
		case 1: str = "Du er m�teleder";
		break;
		case 2: str = "Ny, venter p� svar";
		break;
		case 3: str = "Du har godtatt";
		break;
		case 4: str = "Du har avsl�tt";
		break;
		case 5: str = "Endret, venter p� svar";
		break;
		case 6: str = "M�tet er avlyst";
		break;
		}
		return str;
	}

}
