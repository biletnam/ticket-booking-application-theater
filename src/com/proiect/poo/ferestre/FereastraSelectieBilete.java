package com.proiect.poo.ferestre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.RezervareSpectacol;
import com.proiect.poo.util.Constante;

public class FereastraSelectieBilete extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panou1;
	private JPanel panou2;
	private JButton[] bileteArray = new JButton[9];
	private Integer numarBileteSelectate = 0;
	private RezervareSpectacol rezervareSpectacol;
	
	private final Color CULOARE_BILET_NESELECTAT = new Color(255, 255, 173); 
	private final Color CULOARE_BILET_SELECTAT = new Color(0, 255, 64);

	// constructor ce primeste ca parametru - numele ferestrei
	public FereastraSelectieBilete(String nume, RezervareSpectacol rezervareSpectacol) {
		super(nume);
				
		this.setName(nume);
		this.rezervareSpectacol = rezervareSpectacol;
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(520, 270);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JFrame fereastraAnterioara = Main.mapFerestrePrincipale.get(Constante.FEREASTRA_REPREZENTATII_SPECTACOLE);

				fereastraAnterioara.setVisible(true);
			}
		});

		initializareComponenteGrafice();

		this.getContentPane().add(panou1, BorderLayout.CENTER);
		this.getContentPane().add(panou2, BorderLayout.SOUTH);
	}
	
	// generare culoare bilet in functie de tipul selectiei sale
	private Color generareCuloareButonBilet(Color culoareCurenta) {
		
		if(culoareCurenta.toString().equals(CULOARE_BILET_NESELECTAT.toString())) {
			culoareCurenta = CULOARE_BILET_SELECTAT;
			numarBileteSelectate++;
		} else {
			culoareCurenta = CULOARE_BILET_NESELECTAT;
			numarBileteSelectate--;
		}
				
		return culoareCurenta;
	}
	private void initializareComponenteGrafice() {		
		panou1 = genereazaBilete();
		panou2 = new JPanel();
		
		JButton button1 = new JButton("Calculeaza");
		button1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button1.addMouseListener(new AscultatorButonMouse());
		
		panou2.add(button1);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numarBileteSelectate < rezervareSpectacol.getNumarBileteDorite()) {
					JOptionPane.showMessageDialog(null,"Selectia locurilor este invalida. Sunt necesare un numar de " + rezervareSpectacol.getNumarBileteDorite() + " locuri rezervate!","Eroare",JOptionPane.WARNING_MESSAGE);  
					return;
				} else {
					
					List<LocSpectacol> listaLocuriRezervate = new ArrayList<>();
					
					for(int i=0;i<bileteArray.length;i++) {
						if(bileteArray[i].getBackground().toString().equals(CULOARE_BILET_SELECTAT.toString())){
							listaLocuriRezervate.add(rezervareSpectacol.getLocuriRezervate().get(i));
						}
					}
					
					// vom updata obiectul -> adaugandu-i lista locurilor rezervate de catre utilizatorul aplicatiei
					rezervareSpectacol.setLocuriRezervate(listaLocuriRezervate);
					
					FereastraCalculCostRezervare fereastraCalculCostRezervare = new FereastraCalculCostRezervare(Constante.FEREASTRA_CALCUL_COST_REZERVARE, rezervareSpectacol);
					Main.mapFerestrePrincipale.put(fereastraCalculCostRezervare.getName(), fereastraCalculCostRezervare);

					// inchidem fereastra curenta
					ascundeFereastraCurenta();

					// afisam noua fereastra
					fereastraCalculCostRezervare.setVisible(true);
				}
			}
		});
	}

	private JPanel genereazaBilete() {
		
		JPanel panou = new JPanel(new GridLayout(3, 3));
		panou.setPreferredSize(new Dimension(400,400));
				
		for(int i=0;i<bileteArray.length;i++) {
			// preluam date privind locurile la spectacol (nume, disponibilitate, pret)
			LocSpectacol dateLocSpectacolObj = rezervareSpectacol.getLocuriRezervate().get(i);

			// creare buton
			JButton button = new JButton(dateLocSpectacolObj.getNume());
			button.setEnabled(dateLocSpectacolObj.getDisponibilitate());
			
			if(dateLocSpectacolObj.getDisponibilitate()) {
				button.setBackground(CULOARE_BILET_NESELECTAT);
				
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						JButton butonSelectat = (JButton)e.getSource();
						
						if(numarBileteSelectate == rezervareSpectacol.getNumarBileteDorite() && butonSelectat.getBackground().toString().equalsIgnoreCase(CULOARE_BILET_NESELECTAT.toString()) ) {
							JOptionPane.showMessageDialog(null,"Ati selectat deja numarul maxim de bilete dorite!","Eroare",JOptionPane.WARNING_MESSAGE);  
							return;
						}
						
						butonSelectat.setBackground(generareCuloareButonBilet(butonSelectat.getBackground()));
					}
				});
			}
			
			button.setToolTipText(String.valueOf(dateLocSpectacolObj.getPret()));
			bileteArray[i] = button;
			
			panou.add(button);
		}
		
		return panou;
	}
	
	private void ascundeFereastraCurenta() {
		this.setVisible(false);
	}
}
