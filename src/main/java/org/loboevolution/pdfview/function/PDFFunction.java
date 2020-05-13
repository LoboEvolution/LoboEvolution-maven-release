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

/**
 * PDF Functions are defined in the reference as Section 3.9.
 * @author utente
 * @version $Id: $Id
 */
public abstract class PDFFunction {

    /** Sampled function */
    public static final int TYPE_0 = 0;

    /** Exponential interpolation function */
    public static final int TYPE_2 = 2;

    /** Stitching function. */
    public static final int TYPE_3 = 3;

    /** PostScript calculator function. */
    public static final int TYPE_4 = 4;

    /** the type of this function from the list of known types */
    private final int type;

    /** the input domain of this function, an array of 2 * <i>m</i> floats */
    private float[] domain;

    /** the output range of this functions, and array of 2 * <i>n</i> floats.
     *  required for type 0 and 4 functions
     */
    private float[] range;

    /**
     * Creates a new instance of PDFFunction
     *
     * @param type a int.
     */
    protected PDFFunction (int type) {
        this.type = type;
    }

    /**
     * Get a PDFFunction from a PDFObject
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return a {@link org.loboevolution.pdfview.function.PDFFunction} object.
     * @throws java.io.IOException if any.
     */
    public static PDFFunction getFunction (PDFObject obj)
            throws IOException {
        PDFFunction function;
        int type;
        float[] domain = null;
        float[] range = null;

        // read the function type (required)
        PDFObject typeObj = obj.getDictRef ("FunctionType");
        if (typeObj == null) {
            throw new PDFParseException (
                    "No FunctionType specified in function!");
        }
        type = typeObj.getIntValue ();

        // read the function's domain (required)
        PDFObject domainObj = obj.getDictRef ("Domain");
        if (domainObj == null) {
            throw new PDFParseException ("No Domain specified in function!");
        }

        PDFObject[] domainAry = domainObj.getArray ();
        domain = new float[domainAry.length];
        for (int i = 0; i < domainAry.length; i++) {
            domain[i] = domainAry[i].getFloatValue ();
        }

        // read the function's range (optional)
        PDFObject rangeObj = obj.getDictRef ("Range");
        if (rangeObj != null) {
            PDFObject[] rangeAry = rangeObj.getArray ();
            range = new float[rangeAry.length];
            for (int i = 0; i < rangeAry.length; i++) {
                range[i] = rangeAry[i].getFloatValue ();
            }
        }

        // now create the acual function object
        switch (type) {
            case TYPE_0:
                if (rangeObj == null) {
                    throw new PDFParseException (
                            "No Range specified in Type 0 Function!");
                }
                function = new FunctionType0 ();
                break;
            case TYPE_2:
                function = new FunctionType2 ();
                break;
            case TYPE_3:
                function = new FunctionType3 ();
                break;
            case TYPE_4:
                if (rangeObj == null) {
                    throw new PDFParseException (
                            "No Range specified in Type 4 Function!");
                }
                function = new FunctionType4 ();
                break;
            default:
                throw new PDFParseException (
                        "Unsupported function type: " + type);
        }

        // fill in the domain and optionally the range
        function.setDomain (domain);
        if (range != null) {
            function.setRange (range);
        }

        // now initialize the function
        function.parse (obj);

        return function;
    }

	/**
	 * Perform a linear interpolation.
	 *
	 * @param x the x value of the input
	 * @param xmin the minimum x value
	 * @param ymin the minimum y value
	 * @param xmax the maximum x value
	 * @param ymax the maximum y value
	 * @return the y value interpolated from the given x
	 */
	public static float interpolate(float x, float xmin, float xmax,
			float ymin, float ymax) {
			    float value = (ymax - ymin) / (xmax - xmin);
			    value *= x - xmin;
			    value += ymin;
			    
			    return value;
			}

    /**
     * Get the type of this function
     *
     * @return one of the types of function (0-4)
     */
    public int getType () {
        return this.type;
    }

    /**
     * Get the number of inputs, <i>m</i>, required by this function
     *
     * @return the number of input values expected by this function
     */
    public int getNumInputs () {
        return (this.domain.length / 2);
    }

    /**
     * Get the number of outputs, <i>n</i>, returned by this function
     *
     * @return the number of output values this function will return
     */
    public int getNumOutputs () {
        if (this.range == null) {
            return 0;
        }
        return (this.range.length / 2);
    }

    /**
     * Get a component of the domain of this function
     *
     * @param i the index into the domain array, which has size 2 * <i>m</i>.
     *          the <i>i</i>th entry in the array has index 2<i>i</i>,
     *           2<i>i</i> + 1
     * @return the <i>i</i>th entry in the domain array
     */
    protected float getDomain (int i) {
        return this.domain[i];
    }

    /**
     *  Set the domain of this function
     *
     * @param domain an array of {@link float} objects.
     */
    protected void setDomain (float[] domain) {
        this.domain = domain;
    }

    /**
     * Get a component of the range of this function
     *
     * @param i the index into the range array, which has size 2 * <i>n</i>.
     *          the <i>i</i>th entry in the array has index 2<i>i</i>,
     *           2<i>i</i> + 1
     * @return the <i>i</i>th entry in the range array
     */
    protected float getRange (int i) {
        if (this.range == null) {
            if ((i % 2) == 0) {
                return Float.MIN_VALUE;
            } else {
                return Float.MAX_VALUE;
            }
        }
        return this.range[i];
    }

    /**
     * Set the range of this function
     *
     * @param range an array of {@link float} objects.
     */
    protected void setRange (float[] range) {
        this.range = range;
    }

    /**
     * Map from <i>m</i> input values to <i>n</i> output values.
     * The number of inputs <i>m</i> must be exactly one half the size of the
     * domain.  The number of outputs should match one half the size of the
     * range.
     *
     * @param inputs an array
     * @return the array of <i>n</i> output values
     */
    public float[] calculate (float[] inputs) {
        float[] outputs = new float[getNumOutputs ()];
        calculate (inputs, 0, outputs, 0);
        return outputs;
    }

    /**
     * Map from <i>m</i> input values to <i>n</i> output values.
     * The number of inputs <i>m</i> must be exactly one half the size of the
     * domain.  The number of outputs should match one half the size of the
     * range.
     *
     * @param inputs an array
     * @param inputOffset the offset into the input array to read from
     * @param outputs an array of size 
     * @param outputOffset the offset into the output array to write to
     * @return the array of <i>n</i> output values
     */
    public float[] calculate (float[] inputs, int inputOffset,
                              float[] outputs, int outputOffset) {
        // check the inputs
        if (inputs.length - inputOffset < getNumInputs ()) {
            throw new IllegalArgumentException (
                    "Wrong number of inputs to function!");
        }

        // check the outputs
        if (this.range != null && outputs.length - outputOffset < getNumOutputs ()) {
            throw new IllegalArgumentException (
                    "Wrong number of outputs for function!");
        }

        // clip the inputs to domain
        for (int i = 0; i < inputs.length; i++) {
            // clip to the domain -- min(max(x<i>, domain<2i>), domain<2i+1>)
            inputs[i] = Math.max (inputs[i], getDomain (2 * i));
            inputs[i] = Math.min (inputs[i], getDomain ((2 * i) + 1));
        }

        // do the actual calculation
        doFunction (inputs, inputOffset, outputs, outputOffset);

        // clip the outputs to range
        for (int i = 0; this.range != null && i < outputs.length; i++) {
            // clip to range -- min(max(r<i>, range<2i>), range<2i + 1>)
            outputs[i] = Math.max (outputs[i], getRange (2 * i));
            outputs[i] = Math.min (outputs[i], getRange ((2 * i) + 1));
        }

        return outputs;
    }

    /**
     * Subclasses must implement this method to perform the actual function
     * on the given set of data.  Note that the inputs are guaranteed to be
     * clipped to the domain, while the outputs will be automatically clipped
     * to the range after being returned from this function.
     *
     * @param inputs inputs
     * @param inputOffset the offset into the inputs array to read from
     * @param outputs guaranteed to be at least as big as getNumOutputs(), but not yet clipped to domain
     * @param outputOffset the offset into the output array to write to
     */
    protected abstract void doFunction (float[] inputs, int inputOffset,
                                        float[] outputs, int outputOffset);

    /**
     * Read the function information from a PDF Object
     *
     * @param obj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    protected abstract void parse (PDFObject obj) throws IOException;
}