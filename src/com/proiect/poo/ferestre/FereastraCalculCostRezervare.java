package com.proiect.poo.ferestre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.RezervareSpectacol;
import com.proiect.poo.util.Constante;
import com.proiect.poo.util.JLabelCustomizat;

public class FereastraCalculCostRezervare extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panou1;
	private RezervareSpectacol rezervareSpectacol;
	private JButton buton1;
	private JButton buton2;
	private Eveniment eveniment ;
	public FereastraCalculCostRezervare(String nume, RezervareSpectacol rezervareSpectacol) {
		super(nume);
		
		this.setName(nume);
		this.rezervareSpectacol = rezervareSpectacol;
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(260, 180);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int optiouneSelectata = JOptionPane.showConfirmDialog(
                        null, "Doriti sa anulati rezervarea?",
                        "Anulare rezervare",
                        JOptionPane.YES_NO_OPTION);
				
				if (optiouneSelectata == JOptionPane.YES_OPTION) {
					ascundeFereastraCurenta();
					
					JFrame fereastraNoua = Main.mapFerestrePrincipale.get(Constante.FEREASTRA_PRINCIPALA);

					fereastraNoua.setVisible(true);
				}
			}
		});
		
		initializareComponenteGrafice();
		
		this.add(panou1);
	}
	
	private void ascundeFereastraCurenta() {
		this.setVisible(false);
	}
	
	private void initializareComponenteGrafice() {
		this.panou1 = new JPanel();
		eveniment = new Eveniment();
		
		panou1.setLayout(new GridLayout(2, 1));
		panou1.setBackground(Constante.CULOARE_DEFAULT_PANOU);
		
		JPanel panouSuperior = new JPanel(new FlowLayout(1, 30, 30));
		panouSuperior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JLabel label1 = new JLabelCustomizat("Cost " + (rezervareSpectacol.getLocuriRezervate().size() > 1 ? "abonament" : "bilet"), 10);
		
		double costTotalRezervare = 0.0;
		for(LocSpectacol locRezervat: rezervareSpectacol.getLocuriRezervate() ) {
			costTotalRezervare = costTotalRezervare + locRezervat.getPret();
		}
		
		JLabel label2 = new JLabelCustomizat(String.valueOf(costTotalRezervare) + " RON", 10);
		
		panouSuperior.add(label1);
		panouSuperior.add(label2);
		
		JPanel panouInferior = new JPanel();
		panouInferior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		buton1 = new JButton("Plateste");
		buton1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		buton1.addMouseListener(new AscultatorButonMouse());
		
		buton1.addActionListener(eveniment);
		
		buton2 = new JButton("Anuleaza");
		buton2.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		buton2.addMouseListener(new AscultatorButonMouse());

		buton2.addActionListener(eveniment);
		
		
		panouInferior.add(buton1);
		panouInferior.add(buton2);
		
		panou1.add(panouSuperior);
		panou1.add(panouInferior);
	}
	private class Eveniment implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== buton1) {
				FereastraInformatiiRezervare fereastraInformatiiRezervare = new FereastraInformatiiRezervare(Constante.FEREASTRA_INFORMATII_REZERVARE, rezervareSpectacol);
				Main.mapFerestrePrincipale.put(fereastraInformatiiRezervare.getName(), fereastraInformatiiRezervare);

				// inchidem fereastra curenta
				ascundeFereastraCurenta();

				// afisam noua fereastra
				fereastraInformatiiRezervare.setVisible(true);
				
			}else if(e.getSource()== buton2) {
				int optiouneSelectata = JOptionPane.showConfirmDialog(
                        null, "Doriti sa anulati rezervarea?",
                        "Anulare rezervare",
                        JOptionPane.YES_NO_OPTION);
				
				if (optiouneSelectata == JOptionPane.YES_OPTION) {
					// inchidem fereastra curenta
					ascundeFereastraCurenta();
					JFrame fereastraNoua = Main.mapFerestrePrincipale.get(Constante.FEREASTRA_PRINCIPALA);
					fereastraNoua.setVisible(true);
				}
			}
			
		}
		
	}
}
