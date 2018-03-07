package com.proiect.poo.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;

public class ImprimaDateUtil implements Printable {
	private List<String> dateImprimareList;

	public ImprimaDateUtil(List<String> dateImprimareList) {
		this.dateImprimareList = dateImprimareList;
	}

	public int print(Graphics g, PageFormat pf, int PageIndex) throws PrinterException {
		
		int x = 100;
		// suntem interesati de incrementarea valorii 'y' => pentru o mai buna vizibilitate in pagina
		int y = 100;
		
	    if (PageIndex > 0) {
	         return NO_SUCH_PAGE;
	    }
	    
		Graphics2D g2d = (Graphics2D)g;
	    g2d.translate(pf.getImageableX(), pf.getImageableY());
	    
		g.setColor(Color.black);
		
		for(String informatie: dateImprimareList) {
			g.drawString(informatie, x, y);
			y += 40;
		}

		return PAGE_EXISTS;
	}
}
