package org.loboevolution.pdfview.action;

import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;

/**
 ***************************************************************************
 * URI action, containing a web link
 *
 * @author  Katja Sondermann
 * @since 07.07.2009
 ***************************************************************************
 * @version $Id: $Id
 */
public class UriAction extends PDFAction {

	/** The URL this action links to */
	private String uri;
	
	/**
	 ***********************************************************************
	 * Constructor, reading the URL from the given action object
	 *
	 * @throws java.io.IOException if any. - in case the action can not be parsed
	 ***********************************************************************
	 * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
	 */
	public UriAction(PDFObject obj, PDFObject root) throws IOException {
		super("URI");
		this.uri = PdfObjectParseUtil.parseStringFromDict("URI", obj, true);
	}
	
	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @throws java.io.IOException if any.
	 ***********************************************************************
	 * @param uri a {@link java.lang.String} object.
	 */
	public UriAction(String uri) throws IOException {
		super("URI");
		this.uri = uri;
	}

	/**
	 ***********************************************************************
	 * Get the URI this action directs to
	 *
	 * @return String
	 ***********************************************************************
	 */
	public String getUri() {
		return this.uri;
	}
}
