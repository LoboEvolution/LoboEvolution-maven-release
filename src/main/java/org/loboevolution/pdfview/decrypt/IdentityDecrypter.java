/*
 * Copyright 2008 Pirion Systems Pty Ltd, 139 Warry St,
 * Fortitude Valley, Queensland, Australia
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

package org.loboevolution.pdfview.decrypt;

import java.nio.ByteBuffer;

import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 * Performs identity decryption; that is, inputs aren't encrypted and
 * are returned right back.
 *
 * @author Luke Kirby
 * @version $Id: $Id
 */
public class IdentityDecrypter implements PDFDecrypter {

    private static IdentityDecrypter INSTANCE = new IdentityDecrypter();

    /** {@inheritDoc} */
    @Override
	public ByteBuffer decryptBuffer(String cryptFilterName,
            PDFObject streamObj, ByteBuffer streamBuf)
            throws PDFParseException {

        if (cryptFilterName != null) {
            throw new PDFParseException("This Encryption version does not support Crypt filters");
        }

        return streamBuf;
    }

    /** {@inheritDoc} */
    @Override
	public String decryptString(int objNum, int objGen, String inputBasicString) {
        return inputBasicString;
    }

    /**
     * <p>getInstance.</p>
     *
     * @return a {@link org.loboevolution.pdfview.decrypt.IdentityDecrypter} object.
     */
    public static IdentityDecrypter getInstance() {
        return INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
	public boolean isEncryptionPresent() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
	public boolean isEncryptionPresent(String cryptFilterName) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
	public boolean isOwnerAuthorised() {
        return false;
    }
}