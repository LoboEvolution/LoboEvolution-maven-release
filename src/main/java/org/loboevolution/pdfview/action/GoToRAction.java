package org.loboevolution.pdfview.action;

import java.io.IOException;

import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;

/**
 ***************************************************************************
 * Action directing to a location within another PDF document
 *
 * @author  Katja Sondermann
 * @since 07.07.2009
 ***************************************************************************
 * @version $Id: $Id
 */
public class GoToRAction extends PDFAction {

	/** the destination within the remote PDF file */
    private PDFDestination destination;
    /** the remote file this action refers to*/
    private String file;
    /** Should the remote file be opened in a new window? (optional)*/
    private boolean newWindow=false;
    /**
     * Creates a new instance of GoToRAction from an object
     *
     * @param obj the PDFObject with the action information
     * @throws java.io.IOException if any. - in case the action can not be parsed
     * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
     */
    public GoToRAction(PDFObject obj, PDFObject root) throws IOException {
        super("GoToR");
        // find the destination and parse it
        this.destination = PdfObjectParseUtil.parseDestination("D", obj, root, true);
        
        // find the remote file and parse it
        this.file = PdfObjectParseUtil.parseStringFromDict("F", obj, true);
        
        // find the new window attribute and parse it if available
       	this.newWindow = PdfObjectParseUtil.parseBooleanFromDict("NewWindow", obj, false);
    }    

    /**
     ***********************************************************************
     * Create a new GoToRAction from the given attributes
     *
     * @param dest a {@link org.loboevolution.pdfview.PDFDestination} object.
     * @param file a {@link java.lang.String} object.
     * @param newWindow newWindow
     ***********************************************************************
     */
    public GoToRAction(PDFDestination dest, String file, boolean newWindow){
    	super("GoToR");
    	this.file = file;
    	this.destination = dest;
    	this.newWindow = newWindow;
    }
    
	/**
	 ***********************************************************************
	 * Get the destination this action refers to
	 *
	 * @return PDFDestination
	 ***********************************************************************
	 */
	public PDFDestination getDestination() {
		return this.destination;
	}

	/**
	 ***********************************************************************
	 * Get the file this action refers to
	 *
	 * @return PDFDestination
	 ***********************************************************************
	 */
	public String getFile() {
		return this.file;
	}

	/**
	 ***********************************************************************
	 * Should the remote file be opened in a new window?
	 *
	 * @return boolean
	 ***********************************************************************
	 */
	public boolean isNewWindow() {
		return this.newWindow;
	}
}
