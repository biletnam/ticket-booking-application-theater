package com.proiect.poo;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.proiect.poo.ferestre.FereastraPrincipala;
import com.proiect.poo.util.IncarcaDateInMemorieUtil;
import com.proiect.poo.util.Constante;

public class Main {
	
	// map ce va tine ferestrele principale (util pentru a putea inchide/deschide ferestre in functie de fereastra curenta)
	public static Map<String, JFrame> mapFerestrePrincipale = new HashMap<>();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				IncarcaDateInMemorieUtil.getInstance().incarcaDateInMemorie();

				FereastraPrincipala fereastraPrincipalaObj = new FereastraPrincipala(Constante.FEREASTRA_PRINCIPALA);
				mapFerestrePrincipale.put(fereastraPrincipalaObj.getName(), fereastraPrincipalaObj);				
			}
		});
	}

}
