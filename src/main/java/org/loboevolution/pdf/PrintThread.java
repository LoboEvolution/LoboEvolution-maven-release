/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.pdf;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

/**
 * A thread for printing in.
 *
 * @author utente
 * @version $Id: $Id
 */
public class PrintThread extends Thread {

	/** The pt pages. */
	private PDFPrintPage ptPages;

	/** The pt pjob. */
	private PrinterJob ptPjob;
	
	private PdfDialog dialog;

	/**
	 * Instantiates a new prints the thread.
	 *
	 * @param pages
	 *            the pages
	 * @param pjob
	 *            the pjob
	 * @param dialog a {@link org.loboevolution.pdf.PdfDialog} object.
	 */
	public PrintThread(PDFPrintPage pages, PrinterJob pjob, PdfDialog dialog) {
		ptPages = pages;
		ptPjob = pjob;
		setName(getClass().getName());
		this.dialog = dialog;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			ptPages.show(ptPjob);
			ptPjob.print();
		} catch (PrinterException pe) {
			JOptionPane.showMessageDialog(dialog, "Printing Error: " + pe.getMessage(), "Print Aborted",
					JOptionPane.ERROR_MESSAGE);
		}
		ptPages.hide();
	}
}
