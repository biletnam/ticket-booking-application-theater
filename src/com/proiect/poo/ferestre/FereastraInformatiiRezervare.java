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
import javax.swing.JTextField;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.Persoana;
import com.proiect.poo.model.RezervareSpectacol;
import com.proiect.poo.util.Constante;
import com.proiect.poo.util.IncarcaDateInMemorieUtil;
import com.proiect.poo.util.JLabelCustomizat;
import com.proiect.poo.util.ScrieDateInFisierUtil;

public class FereastraInformatiiRezervare extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panou1;
	private RezervareSpectacol rezervareSpectacol;
	
	public FereastraInformatiiRezervare(String nume, RezervareSpectacol rezervareSpectacol) {
		super(nume);
		
		this.setName(nume);
		this.rezervareSpectacol = rezervareSpectacol;
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(350, 250);
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
		
		panou1.setBackground(Constante.CULOARE_DEFAULT_PANOU);
		
		JPanel panouSuperior = new JPanel(new GridLayout(4, 2, 20, 10));
		panouSuperior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JLabel label1 = new JLabelCustomizat("Nume/Prenume: ", 12);
		JTextField textField1 = new JTextField(8);
		
		JLabel label2 = new JLabelCustomizat("Email: ", 12);
		JTextField textField2 = new JTextField(8);
		
		JLabel label3 = new JLabelCustomizat("Numar telefon: ", 12);
		JTextField textField3 = new JTextField(8);
		
		JLabel label4 = new JLabelCustomizat("Suma plata: ", 12);
		JTextField textField4 = new JTextField(8);
		
		panouSuperior.add(label1);
		panouSuperior.add(textField1);
		
		panouSuperior.add(label2);
		panouSuperior.add(textField2);
		
		panouSuperior.add(label3);
		panouSuperior.add(textField3);
		
		panouSuperior.add(label4);
		panouSuperior.add(textField4);
		
		JPanel panouInferior = new JPanel(new FlowLayout(1,20,20));
		panouInferior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JButton button1 = new JButton("Confirma");
		button1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button1.addMouseListener(new AscultatorButonMouse());
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valoareTextField1 = textField1.getText().trim();
				String valoareTextField2 = textField2.getText().trim();
				String valoareTextField3 = textField3.getText().trim();
				String valoareTextField4 = textField4.getText().trim();
				
				if(valoareTextField1.equalsIgnoreCase("") || !valoareTextField1.matches("[a-zA-Z\\s]+")) {
					JOptionPane.showMessageDialog(null,"Numele este invalid!","Eroare",JOptionPane.WARNING_MESSAGE);  
				} else if(valoareTextField2.equalsIgnoreCase("") || !valoareTextField2.matches("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")) {
					JOptionPane.showMessageDialog(null,"Email este invalid!","Eroare",JOptionPane.WARNING_MESSAGE);
				} else if(valoareTextField3.equalsIgnoreCase("") || !valoareTextField3.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null,"Numarul de telefon este invalid!","Eroare",JOptionPane.WARNING_MESSAGE);
				} else if(valoareTextField4.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null,"Suma este invalida!","Eroare",JOptionPane.WARNING_MESSAGE);
				} else {
					Double valoareSumaIntrodusa = 0.0;
					try {
						valoareSumaIntrodusa = Double.parseDouble(valoareTextField4);
					} catch(NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null,"Suma introdusa este invalida!","Eroare",JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					double costTotalRezervare = 0.0;
					for(LocSpectacol locRezervat: rezervareSpectacol.getLocuriRezervate() ) {
						costTotalRezervare = costTotalRezervare + locRezervat.getPret();
					}
					
					if(valoareSumaIntrodusa < costTotalRezervare) {
						JOptionPane.showMessageDialog(null,"Suma introdusa nu acopera costul rezervarii!","Eroare",JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						double posibilRest = valoareSumaIntrodusa - costTotalRezervare;
						// afisam utilizatorului un mesaj in care ii specificam restul
						if(posibilRest > 0) {
							JOptionPane.showMessageDialog(null,"Restul dumneavoastra: " + posibilRest,"Info",JOptionPane.INFORMATION_MESSAGE);
						}
						
						// trebuie salvat in lista locurile rezervate
						for(LocSpectacol locRezervat: rezervareSpectacol.getLocuriRezervate()) {
							for(LocSpectacol locSpectacol: IncarcaDateInMemorieUtil.getInstance().informatiiLocuriSpectacoleList) {
								if(locSpectacol.getIdentificator() == locRezervat.getIdentificator() && locSpectacol.getNume().equals(locRezervat.getNume())) {
									locSpectacol.setDisponibilitate(false);
								}
							}
						}
						// persoana ce a realizat rezervarea
						Persoana persoana = new Persoana(valoareTextField1, valoareTextField2, valoareTextField3);
						rezervareSpectacol.setPersoana(persoana);
						
						// avand in vedere ca persoana a platit => rezervarea la spectacol este realizata cu succes
						ScrieDateInFisierUtil.getInstance().scrieDateInFisier(rezervareSpectacol);
						
						FereastraBilet fereastraBilet = new FereastraBilet(Constante.FEREASTRA_BILET, rezervareSpectacol);
						Main.mapFerestrePrincipale.put(fereastraBilet.getName(), fereastraBilet);

						// inchidem fereastra curenta
						ascundeFereastraCurenta();

						// afisam noua fereastra
						fereastraBilet.setVisible(true);
					}
				}
			}
		});
		
		JButton button2 = new JButton("Anuleaza");
		button2.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button2.addMouseListener(new AscultatorButonMouse());

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		});
		
		panouInferior.add(button1);
		panouInferior.add(button2);
		
		panou1.add(panouSuperior);
		panou1.add(panouInferior);
	}
}
