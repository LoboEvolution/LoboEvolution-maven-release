/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/**
 * An array view that stores 64-bit quantities and implements the JavaScript "Float64Array" interface.
 * It also implements List&lt;Double&gt; for direct manipulation in Java.
 *
 * @author utente
 * @version $Id: $Id
 */
public class NativeFloat64Array
    extends NativeTypedArrayView<Double>
{
    private static final long serialVersionUID = -1255405650050639335L;

    private static final String CLASS_NAME = "Float64Array";
    private static final int BYTES_PER_ELEMENT = 8;

    /**
     * <p>Constructor for NativeFloat64Array.</p>
     */
    public NativeFloat64Array()
    {
    }

    /**
     * <p>Constructor for NativeFloat64Array.</p>
     *
     * @param ab a {@link org.mozilla.javascript.typedarrays.NativeArrayBuffer} object.
     * @param off a int.
     * @param len a int.
     */
    public NativeFloat64Array(NativeArrayBuffer ab, int off, int len)
    {
        super(ab, off, len, len * BYTES_PER_ELEMENT);
    }

    /**
     * <p>Constructor for NativeFloat64Array.</p>
     *
     * @param len a int.
     */
    public NativeFloat64Array(int len)
    {
        this(new NativeArrayBuffer(len * BYTES_PER_ELEMENT), 0, len);
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName()
    {
        return CLASS_NAME;
    }

    /**
     * <p>init.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param sealed a boolean.
     */
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        NativeFloat64Array a = new NativeFloat64Array();
        a.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /** {@inheritDoc} */
    @Override
    protected NativeFloat64Array construct(NativeArrayBuffer ab, int off, int len)
    {
        return new NativeFloat64Array(ab, off, len);
    }

    /** {@inheritDoc} */
    @Override
    public int getBytesPerElement()
    {
        return BYTES_PER_ELEMENT;
    }

    /** {@inheritDoc} */
    @Override
    protected NativeFloat64Array realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (!(thisObj instanceof NativeFloat64Array)) {
            throw incompatibleCallError(f);
        }
        return (NativeFloat64Array)thisObj;
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_get(int index)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        long base = ByteIo.readUint64Primitive(arrayBuffer.buffer, (index * BYTES_PER_ELEMENT) + offset, useLittleEndian());
        return Double.longBitsToDouble(base);
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_set(int index, Object c)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        double val = ScriptRuntime.toNumber(c);
        long base = Double.doubleToLongBits(val);
        ByteIo.writeUint64(arrayBuffer.buffer, (index * BYTES_PER_ELEMENT) + offset, base, useLittleEndian());
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Double get(int i)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double)js_get(i);
    }

    /** {@inheritDoc} */
    @Override
    public Double set(int i, Double aByte)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double)js_set(i, aByte);
    }
}