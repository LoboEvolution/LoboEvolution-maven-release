/*
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.loboevolution.pdfview.function;

import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

public class FunctionType3 extends PDFFunction {
    
    private PDFFunction[] functions;
    private float[] bounds;
    private float[] encode;
    
    /**
     * Creates a new instance of FunctionType3
     */
    protected FunctionType3() {
        super(TYPE_3);
    }

    @Override
	protected void parse(PDFObject obj) throws IOException {
        // read the Functions array (required)
        PDFObject functionsObj = obj.getDictRef("Functions");
        if (functionsObj == null) {
            throw new PDFParseException("Functions required for function type 3!");
        }
        PDFObject[] functionsAry = functionsObj.getArray();
        functions = new PDFFunction[functionsAry.length];
        for (int i = 0; i < functionsAry.length; i++) {
        	functions[i] = PDFFunction.getFunction(functionsAry[i]);
        }
        
        // read the Bounds array (required)
        PDFObject boundsObj = obj.getDictRef("Bounds");
        if (boundsObj == null) {
            throw new PDFParseException("Bounds required for function type 3!");
        }
        PDFObject[] boundsAry = boundsObj.getArray();
        bounds = new float[boundsAry.length + 2];
        if (bounds.length - 2 != functions.length - 1) {
        	throw new PDFParseException("Bounds array must be of length " + (functions.length - 1));
        }
        
        for (int i = 0; i < boundsAry.length; i++) {
            bounds[i+1] = boundsAry[i].getFloatValue();
        }
        bounds[0] = getDomain(0);
        bounds[bounds.length-1] = getDomain(1);

        // read the encode array (required)
        PDFObject encodeObj = obj.getDictRef("Encode");
        if (encodeObj == null) {
            throw new PDFParseException("Encode required for function type 3!");
        }
        PDFObject[] encodeAry = encodeObj.getArray();
        encode = new float[encodeAry.length];
        if (encode.length != 2*functions.length) {
        	throw new PDFParseException("Encode array must be of length " + 2*functions.length);
        }
        for (int i = 0; i < encodeAry.length; i++) {
            encode[i] = encodeAry[i].getFloatValue();
        }
    }

	/** {@inheritDoc} */
    @Override
	protected void doFunction(float[] inputs, int inputOffset,
			float[] outputs, int outputOffset) {
    	
    	float x = inputs[inputOffset];

    	// calculate the output values
    	int p = bounds.length - 2;
    	while (x < bounds[p]) p--;
    	x = interpolate(x, bounds[p], bounds[p+1], encode[2*p], encode[2*p + 1]);
    	float[] out = functions[p].calculate(new float[]{x});
    	for (int i = 0; i < out.length; i++) {
    		outputs[i + outputOffset] = out[i];
    	}
    }
    
    /** {@inheritDoc} */
    @Override
    public int getNumInputs() {
    	return 1;
    }
    
    /** {@inheritDoc} */
    @Override
    public int getNumOutputs() {
    	return functions[0].getNumOutputs();
    }
}
