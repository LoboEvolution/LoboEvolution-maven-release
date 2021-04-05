/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
* Main Developer: Simon Barnett
*
* 	This file is part of JPedal
*
* Copyright (c) 2008, IDRsolutions
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the IDRsolutions nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY IDRsolutions ``AS IS'' AND ANY
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL IDRsolutions BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* Other JBIG2 image decoding implementations include
* jbig2dec (http://jbig2dec.sourceforge.net/)
* xpdf (http://www.foolabs.com/xpdf/)
* 
* The final draft JBIG2 specification can be found at http://www.jpeg.org/public/fcd14492.pdf
* 
* All three of the above resources were used in the writing of this software, with methodologies,
* processes and inspiration taken from all three.
*
* ---------------
* GenericRegionFlags.java
* ---------------
*/
package org.jpedal.jbig2.segment.region.generic;

import java.util.logging.Logger;

import org.jpedal.jbig2.decoders.JBIG2StreamDecoder;
import org.jpedal.jbig2.segment.Flags;

/**
 * <p>GenericRegionFlags class.</p>
 *
  *
  *
 */
public class GenericRegionFlags extends Flags {

	private static final Logger logger = Logger.getLogger(GenericRegionFlags.class.getName());
	/** Constant <code>MMR="MMR"</code> */
	public static final String MMR = "MMR";
	/** Constant <code>GB_TEMPLATE="GB_TEMPLATE"</code> */
	public static final String GB_TEMPLATE = "GB_TEMPLATE";
	/** Constant <code>TPGDON="TPGDON"</code> */
	public static final String TPGDON = "TPGDON";

	/** {@inheritDoc} */
	public void setFlags(int flagsAsInt) {
		this.flagsAsInt = flagsAsInt;
		
		/** extract MMR */
		flags.put(MMR, flagsAsInt & 1);
		
		/** extract GB_TEMPLATE */
		flags.put(GB_TEMPLATE, (flagsAsInt >> 1) & 3);
		
		/** extract TPGDON */
		flags.put(TPGDON, (flagsAsInt >> 3) & 1);
		
		
		if(JBIG2StreamDecoder.debug)
			logger.info("flags: " + flags);
	}
}
