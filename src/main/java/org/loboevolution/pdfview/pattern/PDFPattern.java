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

package org.loboevolution.pdfview.pattern;


import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Map;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPaint;
import org.loboevolution.pdfview.PDFParseException;

/**
 * The abstract superclass of all PDF Pattern types
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class PDFPattern {
 
    /** the pattern type (1 or 2) */
    private int type;
    
    /** the matrix to transform from pattern space to PDF space */
    private AffineTransform xform;
 
    /**
     * Creates a new instance of PDFPattern
     *
     * @param type a int.
     */
    protected PDFPattern(int type) 
    {
        this.type = type;
    }
 
    /**
     * Read a pattern from the given pattern stream
     *
     * @param patternObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @param resources a {@link java.util.Map} object.
     * @return a {@link org.loboevolution.pdfview.pattern.PDFPattern} object.
     * @throws java.io.IOException if any.
     */
    public static PDFPattern getPattern(PDFObject patternObj, Map resources)
        throws IOException
    {
        // see if the pattern is already cached
        PDFPattern pattern = (PDFPattern) patternObj.getCache();
        if (pattern != null) {
            return pattern;
        }
        
        // get the pattern type
        int type = patternObj.getDictRef("PatternType").getIntValue();
         
        // read the pattern transform matrix
        PDFObject matrix = patternObj.getDictRef("Matrix");
        AffineTransform xform = null;
        if (matrix == null) {
            xform = new AffineTransform();
        } else {
            float elts[]= new float[6];
            for (int i = 0; i < elts.length; i++) {
            	elts[i] = matrix.getAt(i).getFloatValue();
            }
        
            xform = new AffineTransform(elts); 
        }    
        
        switch (type) {
            case 1:
                pattern = new PatternType1();
                break;
            case 2:
            	pattern = new PatternType2();
            	break;
            default:
                throw new PDFParseException("Unknown pattern type " + type);
        }       
        
        // set the transform
        pattern.setTransform(xform);
        
        // parse the pattern-specific data
        pattern.parse(patternObj, resources);
        
        // set the cache
        patternObj.setCache(pattern);
        
        return pattern;
    }
    
    /**
     * Get the type of this pattern
     *
     * @return a int.
     */
    public int getPatternType() {
        return this.type;
    }
    
    /**
     * Get the transform associated with this pattern
     *
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    public AffineTransform getTransform() {
        return this.xform;
    }
    
    /**
     * Set the transform associated with this pattern
     *
     * @param xform a {@link java.awt.geom.AffineTransform} object.
     */
    protected void setTransform(AffineTransform xform) {
        this.xform = xform;
    }
    
    /**
     * Parse the pattern-specific information from the pdf object
     *
     * @param patternObj the pdfobject with data for this pattern
     * @param resources a {@link java.util.Map} object.
     * @throws java.io.IOException if any.
     */
    protected abstract void parse(PDFObject patternObj, Map resources) 
        throws IOException;
    
    /**
     * Returns paint that represents the selected pattern
     *
     * @param basePaint the background paint color, or null for none
     * @return a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public abstract PDFPaint getPaint(PDFPaint basePaint);
}
