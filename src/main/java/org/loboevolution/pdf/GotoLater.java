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

/**
 * Simple runnable to tell listeners that the page has changed.
 *
 * @author utente
 * @version $Id: $Id
 */
public class GotoLater implements Runnable {

	/** The page. */
	private int page;
	
	private ThumbPanel thumb;

	/**
	 * <p>Constructor for GotoLater.</p>
	 *
	 * @param pagenum a int.
	 * @param thumb a {@link org.loboevolution.pdf.ThumbPanel} object.
	 */
	public GotoLater(int pagenum,  ThumbPanel thumb) {
		page = pagenum;
		this.thumb = thumb;
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		if (thumb.getListener() != null) {
			thumb.getListener().gotoPage(page);
		}
	}
}
