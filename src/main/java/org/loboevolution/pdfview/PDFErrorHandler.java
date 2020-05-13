package org.loboevolution.pdfview;

/**
 * Simple class to handle exceptions - as default we just print the stack trace
 * but it's possible to inject another behaviour
 *
 * @author xond
 * @version $Id: $Id
 */
public class PDFErrorHandler {

    /**
     * <p>publishException.</p>
     *
     * @param e a {@link java.lang.Throwable} object.
     */
    public void publishException(Throwable e){
       e.printStackTrace(); 
    }
}
