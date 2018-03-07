package com.proiect.poo.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.RezervareSpectacol;

//clasa ce va utiliza design pattern (singleton)
public class ScrieDateInFisierUtil {

	// variabila instanta
	private static ScrieDateInFisierUtil instance;

	public static ScrieDateInFisierUtil getInstance() {
		if (instance == null) {
			instance = new ScrieDateInFisierUtil();
		}

		return instance;
	}

	// constructor privat
	private ScrieDateInFisierUtil() {

	}
	
	public void scrieDateInFisier(RezervareSpectacol rezervareSpectacol) {
		scrieRezervareInFisier(rezervareSpectacol);
		updateazaInformatiiLocuriSpectacole(rezervareSpectacol);
	}

	private void scrieRezervareInFisier(RezervareSpectacol rezervareSpectacol) {

		try (FileWriter fileWriter = new FileWriter(Constante.FISIER_REZERVARI_SPECTACOLE, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

			bufferedWriter.append(rezervareSpectacol.toString());
			bufferedWriter.newLine();
			
			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateazaInformatiiLocuriSpectacole(RezervareSpectacol rezervareSpectacol) {
		try (FileWriter fileWriter = new FileWriter(Constante.FISIER_INFORMATII_LOCURI_SPECTACOLE);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
			
			// lista locurilor rezervate
			List<LocSpectacol> locuriRezervate = rezervareSpectacol.getLocuriRezervate();
			
			// lista locurilor curente ale tuturor reprezentatiilor
			List<LocSpectacol> locuriSpectacoleList = IncarcaDateInMemorieUtil.getInstance().informatiiLocuriSpectacoleList;
			
			// vom updata lista de locuri ale spectacolelor prin modificarea tipului de loc in indisponibil
			// conform listei de locuri rezervate
			for(LocSpectacol locSpectacol: locuriSpectacoleList) {
				// folosind metoda 'equals' din clasa LocSpectacol
				if(locuriRezervate.contains(locSpectacol)) {
					locSpectacol.setDisponibilitate(false);
				}
				
				String disponibilitateLoc = (locSpectacol.getDisponibilitate() ? "disponibil": "indisponibil");
				
				// vom folosi formatul corect de scriere in fisier
				bufferedWriter.append(locSpectacol.getIdentificator() + ", " + locSpectacol.getNume() + ", " + disponibilitateLoc + ", " + locSpectacol.getPret());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
