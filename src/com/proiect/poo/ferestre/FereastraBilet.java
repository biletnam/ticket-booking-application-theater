package com.proiect.poo.ferestre;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.RezervareSpectacol;
import com.proiect.poo.util.Constante;
import com.proiect.poo.util.ImprimaDateUtil;
import com.proiect.poo.util.JLabelCustomizat;
import com.proiect.poo.util.TrimiteMailUtil;

public class FereastraBilet extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panou1;
	private RezervareSpectacol rezervareSpectacol;

	public FereastraBilet(String nume, RezervareSpectacol rezervareSpectacol) {
		super(nume);

		this.setName(nume);
		this.rezervareSpectacol = rezervareSpectacol;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(370, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JFrame fereastraAnterioara = Main.mapFerestrePrincipale.get(Constante.FEREASTRA_PRINCIPALA);

				fereastraAnterioara.setVisible(true);
			}
		});

		initializareComponenteGrafice();

		this.add(panou1);
		
		validareTrimitereConfirmare();
		
	}
	
	// trimitere email confirmare daca data curenta corespunde cu data rezervarii
	private void validareTrimitereConfirmare() {
		//
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();             
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
		String dateFormat = simpleDateFormat.format(date);
		
		if(dateFormat.equals(rezervareSpectacol.getDataReprezentatie())) {
			// fir de executie necesar pentru a trimite un email intr-un mod concurent
			// altfel, fereastra noua nu va putea fi afisata corespunzator
			// (codul executandu-se intr-o maniera sincrona)
			Thread firExecutie = new Thread() {
				public void run() {
					TrimiteMailUtil.getInstante().trimiteEmail(rezervareSpectacol);
				}
			};
			firExecutie.start();
			
		}
		//
	}

	private void initializareComponenteGrafice() {
		this.panou1 = new JPanel();

		panou1.setLayout(new FlowLayout(1, 0, 0));
		panou1.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JPanel panouSuperior = new JPanel(new GridLayout(3, 2, 20, 20));
		panouSuperior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JLabel label = new JLabelCustomizat("Nume spectacol: ", 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		label = new JLabelCustomizat(rezervareSpectacol.getNumeSpectacol(), 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		label = new JLabelCustomizat("Data/Ora reprezentatie: ", 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		label = new JLabelCustomizat(
				rezervareSpectacol.getDataReprezentatie() + " " + rezervareSpectacol.getOraReprezentatie(), 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		label = new JLabelCustomizat("Locuri rezervate: ", 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		String locuriRezervate = "";
		for (LocSpectacol locRezervat : rezervareSpectacol.getLocuriRezervate()) {
			locuriRezervate = locuriRezervate + locRezervat.getNume() + " ";
		}

		label = new JLabelCustomizat(locuriRezervate, 10);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panouSuperior.add(label);

		JPanel panouInferior = new JPanel(new FlowLayout(1, 20, 20));
		panouInferior.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		JButton button1 = new JButton("Print");
		button1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button1.addMouseListener(new AscultatorButonMouse());

		List<String> dateImprimareList = new ArrayList<>();
		dateImprimareList.add("Nume spectacol: " + rezervareSpectacol.getNumeSpectacol());
		dateImprimareList.add("Data/Ora reprezentatie: " + rezervareSpectacol.getDataReprezentatie() + " "
				+ rezervareSpectacol.getOraReprezentatie());
		dateImprimareList.add("Locuri Rezervate: " + locuriRezervate);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PrinterJob printerJob = PrinterJob.getPrinterJob();
				printerJob.setPrintable(new ImprimaDateUtil(dateImprimareList));

				if (printerJob.printDialog())
					try {
						printerJob.print();
					} catch (PrinterException pe) {
						System.err.println("Nu s-au putut printa informatiile: " + pe.getMessage());
					}
			}
		});

		panouInferior.add(button1);

		panou1.add(panouSuperior);
		panou1.add(panouInferior);
	}
}
