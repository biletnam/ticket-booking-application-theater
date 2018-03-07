package com.proiect.poo.ascultatori;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.proiect.poo.util.Constante;

public class AscultatorButonMouse extends MouseAdapter
{
	public void mouseEntered(MouseEvent e)
	{
		JButton butonSelectat = (JButton)e.getSource();
		if(butonSelectat.isEnabled())
			butonSelectat.setBackground(new Color(255, 255, 255));
	}
	
	public void mouseExited(MouseEvent e)
	{
		JButton butonSelectat = (JButton)e.getSource();
		if(butonSelectat.isEnabled()) {
			butonSelectat.setBackground(Constante.CULOARE_DEFAULT_BUTON);
		}
	}
}