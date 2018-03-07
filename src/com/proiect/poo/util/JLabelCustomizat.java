package com.proiect.poo.util;

import javax.swing.JLabel;

// clasa ce va putea fi apelata cand dorim sa construim un obiect JLabel 
// folosind html si o anumita inaltime pentru litere
public class JLabelCustomizat extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// constructor
	public JLabelCustomizat(String text, Integer size) {
		// apel constructor parinte (insa customizat)
		super("<html><span style='font-size:" + size + "px'>" + text + "</span></html>");
		
		
	}
}
