package com.proiect.poo.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.ReprezentatieSpectacol;

// clasa ce va utiliza design pattern (singleton)
/*
 * 1. variabila instanta
 * 2. constructor privat
 * 3. metoda ce instantiaza o singura data acest tip de clasa
 */
public class IncarcaDateInMemorieUtil {
	private static IncarcaDateInMemorieUtil instance;
	
	public List<String> numeSpectacoleList;
	public List<ReprezentatieSpectacol> reprezentatiiSpectacoleList;
	public List<LocSpectacol> informatiiLocuriSpectacoleList;
	
	public static IncarcaDateInMemorieUtil getInstance() {
		if(instance == null) {
			instance = new IncarcaDateInMemorieUtil();
		}
		
		return instance;
	}
	// constructor privat
	private IncarcaDateInMemorieUtil() {
		
	}
	
	public void incarcaDateInMemorie() {
		numeSpectacoleList = citireNumeSpectacole();
		reprezentatiiSpectacoleList = citireReprezentatiiSpectacole();
		informatiiLocuriSpectacoleList = citireDateLocuriPerIdentificatorSpectacol();	
	}
	
	private List<String> citireNumeSpectacole() {
		
		List<String> numeSpectacole = new ArrayList<>();
		
		try (FileReader fileReader = new FileReader(Constante.FISIER_NUME_SPECTACOLE);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {
			
			String linie = null;
			
			while((linie = bufferedReader.readLine()) != null) {
				numeSpectacole.add(linie.trim());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return numeSpectacole;
	}
	
	private List<ReprezentatieSpectacol> citireReprezentatiiSpectacole() {
		
		List<ReprezentatieSpectacol> reprezentatiiSpectacole = new ArrayList<>();
		
		try (FileReader fileReader = new FileReader(Constante.FISIER_REPREZENTATII_SPECTACOLE);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {
			
			String linie = null;
			
			while((linie = bufferedReader.readLine()) != null) {
				String [] splitLinie = linie.split(",");
				
				ReprezentatieSpectacol spectacol = new ReprezentatieSpectacol(Integer.parseInt(splitLinie[0].trim()), splitLinie[1].trim(), splitLinie[2].trim(), splitLinie[3].trim());
				reprezentatiiSpectacole.add(spectacol);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reprezentatiiSpectacole;
	}
	
	private List<LocSpectacol> citireDateLocuriPerIdentificatorSpectacol() {
		
		List<LocSpectacol> informatiiLocuriSpectacole = new ArrayList<>();
		
		try (FileReader fileReader = new FileReader(Constante.FISIER_INFORMATII_LOCURI_SPECTACOLE);
				BufferedReader bufferedReader = new BufferedReader(fileReader);) {
			
			String linie = null;
			
			while((linie = bufferedReader.readLine()) != null) {
				String [] splitLinie = linie.split(",");
				
				boolean locDisponibil = false;
				
				if(splitLinie[2].trim().equalsIgnoreCase("disponibil")) {
					locDisponibil = true;
				}
				
				LocSpectacol loc = new LocSpectacol(Integer.parseInt(splitLinie[0].trim()), splitLinie[1].trim(), locDisponibil, Double.valueOf(splitLinie[3].trim()));
				informatiiLocuriSpectacole.add(loc);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return informatiiLocuriSpectacole;
	}
}
