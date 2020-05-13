package org.loboevolution.pdfview.function.postscript.operation;

import java.util.LinkedList;



/**
 * <p>Expression class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class Expression extends LinkedList<Object> {

    /** {@inheritDoc} */
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof Expression) {
            // actually validate the list contents are the same expressions
            return true;
        }
        return false;
    }
}
