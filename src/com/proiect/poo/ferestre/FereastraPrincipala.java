package com.proiect.poo.ferestre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.util.Constante;
import com.proiect.poo.util.IncarcaDateInMemorieUtil;
import com.proiect.poo.util.JLabelCustomizat;

public class FereastraPrincipala extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panouPrincipal;
	
	// constructor ce primeste ca parametru - numele ferestrei
	public FereastraPrincipala(String nume) {
		super(nume);
		
		// proprietati fereastra
		this.setName(nume);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 230);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
			}
		});
		
		initializareComponenteGrafice();
		
		this.add(panouPrincipal);
	}
	
	private void ascundeFereastraCurenta() {
		this.setVisible(false);
	}
	
	private void initializareComponenteGrafice() {
		this.panouPrincipal = new JPanel();
		
		JLabel label1 = new JLabelCustomizat("Selectati spectacolul dorit: ", 12);
		
		JComboBox<Object> comboBox1 = genereazaListaSpectacole();
		
		JButton button1 = new JButton("Confirma");
		button1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button1.addMouseListener(new AscultatorButonMouse());
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectieNumeSpectacol = String.valueOf(comboBox1.getSelectedItem());
				
				FereastraReprezentatiiSpectacole fereastraReprezentatiiSpectacole = new FereastraReprezentatiiSpectacole(Constante.FEREASTRA_REPREZENTATII_SPECTACOLE, selectieNumeSpectacol);
				Main.mapFerestrePrincipale.put(fereastraReprezentatiiSpectacole.getName(), fereastraReprezentatiiSpectacole);
				
				// inchidem fereastra curenta
				ascundeFereastraCurenta();

				// afisam noua fereastra
				fereastraReprezentatiiSpectacole.setVisible(true);
			}
		});
		panouPrincipal.setLayout(new GridLayout(2,1));
		
		JPanel panouSuperior = new JPanel(new FlowLayout(1, 20, 20));
		panouSuperior.setBackground(Constante.CULOARE_DEFAULT_PANOU);
		panouSuperior.add(label1);
		panouSuperior.add(comboBox1);
		
		panouPrincipal.add(panouSuperior);
		
		JPanel panouInferior = new JPanel(new FlowLayout(1, 20, 20));
		panouInferior.setBackground(Constante.CULOARE_DEFAULT_PANOU);
		panouInferior.add(button1);
		
		panouPrincipal.add(panouInferior);
	}
	
	private JComboBox<Object> genereazaListaSpectacole() {
		// preluare date privind numele spectacolelor
		Object [] numeSpectacoleArray = (Object[]) IncarcaDateInMemorieUtil.getInstance().numeSpectacoleList.toArray();

		// creare lista si adaugare model
		JComboBox<Object> comboBoxObj = new JComboBox<Object>(numeSpectacoleArray);
		
		return comboBoxObj;
	}
}
