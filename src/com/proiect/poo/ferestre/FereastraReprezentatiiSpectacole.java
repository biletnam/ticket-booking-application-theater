package com.proiect.poo.ferestre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.proiect.poo.Main;
import com.proiect.poo.ascultatori.AscultatorButonMouse;
import com.proiect.poo.model.LocSpectacol;
import com.proiect.poo.model.ReprezentatieSpectacol;
import com.proiect.poo.model.RezervareSpectacol;
import com.proiect.poo.util.Constante;
import com.proiect.poo.util.IncarcaDateInMemorieUtil;
import com.proiect.poo.util.JLabelCustomizat;

public class FereastraReprezentatiiSpectacole extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panouPrincipal;
	private JPanel panouSecundar;
	private JTable table1;
	private JTextField textField1;
	private String spectacol;
	private DefaultTableModel defaultTableModel;

	// constructor ce primeste ca parametru - numele ferestrei
	public FereastraReprezentatiiSpectacole(String nume, String spectacol) {
		super(nume);

		this.spectacol = spectacol;

		this.setName(nume);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(560, 270);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JFrame fereastraNoua = Main.mapFerestrePrincipale.get(Constante.FEREASTRA_PRINCIPALA);

				fereastraNoua.setVisible(true);
			}
		});

		initializareComponenteGrafice();

		this.getContentPane().add(panouPrincipal, BorderLayout.CENTER);
		this.getContentPane().add(panouSecundar, BorderLayout.SOUTH);
	}

	private void ascundeFereastraCurenta() {
		this.setVisible(false);
	}

	private void initializareComponenteGrafice() {
		panouPrincipal = new JPanel();
		panouPrincipal.setBackground(Constante.CULOARE_DEFAULT_PANOU);

		panouSecundar = new JPanel();

		JScrollPane scroolPane1 = genereazaDateBileteDisponibile();
		panouPrincipal.add(scroolPane1);

		JLabel label1 = new JLabelCustomizat("Numar de bilete dorite: ", 9);

		textField1 = new JTextField(10);

		JButton button1 = new JButton("Confirma");
		button1.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		button1.addMouseListener(new AscultatorButonMouse());

		panouSecundar.add(label1);
		panouSecundar.add(textField1);
		panouSecundar.add(button1);

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// preluam date din textfield
				String numarBileteDorite = textField1.getText();
				Integer numarRandSelectat = table1.getSelectedRow();

				if (numarRandSelectat == -1) {
					JOptionPane.showMessageDialog(null, "Nu ati selectat o reprezentatie!", "Eroare",
							JOptionPane.WARNING_MESSAGE);
				} else if (!numarBileteDorite.matches("[1-9]")) {
					JOptionPane.showMessageDialog(null, "Trebuie introdus un numar de bilete in intervalul [1-9]!",
							"Eroare", JOptionPane.WARNING_MESSAGE);
				} else {
					Integer identifactorSelectieTabel = (Integer) defaultTableModel.getValueAt(numarRandSelectat, 0);
					String numeSpectacol = (String) defaultTableModel.getValueAt(numarRandSelectat, 1);
					String dataReprezentatie = (String) defaultTableModel.getValueAt(numarRandSelectat, 2);
					String oraReprezentatie = (String) defaultTableModel.getValueAt(numarRandSelectat, 3);
					
					Integer numarBileteDisponibileSelectieTabel = (Integer) defaultTableModel
							.getValueAt(numarRandSelectat, 4);

					if (Integer.parseInt(numarBileteDorite) > numarBileteDisponibileSelectieTabel) {
						JOptionPane.showMessageDialog(null, "Numar invalid de bilete dorite!", "Eroare",
								JOptionPane.WARNING_MESSAGE);
					} else {

						List<LocSpectacol> locuriRezervateList = new ArrayList<>();

						// vom identifica lista locurilor (disponibile/indisponibile) -> per
						// reprezentatia selectata din tabel
						for (LocSpectacol locSpectacol : IncarcaDateInMemorieUtil
								.getInstance().informatiiLocuriSpectacoleList) {
							if (locSpectacol.getIdentificator() == identifactorSelectieTabel) {
								locuriRezervateList.add(locSpectacol);
							}
						}
						// vom transfera acest obiect pentru ferestrele urmatoare -> contine toate
						// informatiile necesare procesarii rapide a datelor
						RezervareSpectacol rezervareSpectacol = new RezervareSpectacol();
						rezervareSpectacol.setIdentificator(identifactorSelectieTabel);
						rezervareSpectacol.setNumeSpectacol(numeSpectacol);
						rezervareSpectacol.setDataReprezentatie(dataReprezentatie);
						rezervareSpectacol.setOraReprezentatie(oraReprezentatie);
						rezervareSpectacol.setLocuriRezervate(locuriRezervateList);
						rezervareSpectacol.setNumarBileteDorite(Integer.parseInt(numarBileteDorite));
						rezervareSpectacol.setRezervareConfirmata(false);
						
						FereastraSelectieBilete fereastraSelectieBilete = new FereastraSelectieBilete(
								Constante.FEREASTRA_SELECTIE_BILETE, rezervareSpectacol);
						Main.mapFerestrePrincipale.put(fereastraSelectieBilete.getName(), fereastraSelectieBilete);

						// inchidem fereastra curenta
						ascundeFereastraCurenta();

						// afisam noua fereastra
						fereastraSelectieBilete.setVisible(true);
					}
				}
			}
		});
	}

	private JScrollPane genereazaDateBileteDisponibile() {
		String coloane[] = { "Id", "Nume", "Data", "Ora", "Bilete Libere" };

		// vom atasa tabelului date dinamice -> vom folosit DefaultTableModel
		defaultTableModel = new DefaultTableModel(coloane, 0) {
			private static final long serialVersionUID = 1L;

			// vom suprascrie metoda pentru a nu permite modificarea randurilor din tabel
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (ReprezentatieSpectacol reprezentatieSpectacol : IncarcaDateInMemorieUtil
				.getInstance().reprezentatiiSpectacoleList) {
			if (reprezentatieSpectacol.getNume().equals(this.spectacol)) {
				int identificatorReprezentatieSpectacol = reprezentatieSpectacol.getIdentificator();
				int numarBileteDisponibile = 0;

				for (LocSpectacol locSpectacol : IncarcaDateInMemorieUtil.getInstance().informatiiLocuriSpectacoleList) {
					if (locSpectacol.getIdentificator() == identificatorReprezentatieSpectacol
							&& locSpectacol.getDisponibilitate()) {
						numarBileteDisponibile++;
					}
				}

				if (numarBileteDisponibile > 0) {
					Object[] dateReprezentatieSpectacol = { reprezentatieSpectacol.getIdentificator(),
							reprezentatieSpectacol.getNume(), reprezentatieSpectacol.getData(),
							reprezentatieSpectacol.getOra(), numarBileteDisponibile,
							reprezentatieSpectacol.getIdentificator() };
					defaultTableModel.addRow(dateReprezentatieSpectacol);
				}

			}
		}

		table1 = new JTable(defaultTableModel);

		// adaug tabelul intr-un obiect JScrollPane
		JScrollPane scrollPane = new JScrollPane(table1);
		scrollPane.setPreferredSize(new Dimension(490, 150));
		return scrollPane;
	}
}
