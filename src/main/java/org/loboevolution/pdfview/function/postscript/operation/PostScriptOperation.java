package org.loboevolution.pdfview.function.postscript.operation;

import java.util.Stack;

/**
 * <p>PostScriptOperation interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface PostScriptOperation {

    /**
     * evaluate the function, popping the stack as needed and pushing results.
     *
     * @param environment a {@link java.util.Stack} object.
     */
    public void eval(Stack<Object> environment);

}

