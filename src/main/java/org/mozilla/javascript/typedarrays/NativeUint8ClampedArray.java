/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/**
 * An array view that stores 8-bit quantities and implements the JavaScript "Uint8ClampedArray" interface.
 * It also implements List&lt;Integer&gt; for direct manipulation in Java. Bytes inserted that fall out of the range
 * (0 &lt;= X &lt; 256) will be adjusted so that they match before insertion.
 *
 *
 *
 */
public class NativeUint8ClampedArray
    extends NativeTypedArrayView<Integer>
{
    private static final long serialVersionUID = -3349419704390398895L;

    private static final String CLASS_NAME = "Uint8ClampedArray";

    /**
     * <p>Constructor for NativeUint8ClampedArray.</p>
     */
    public NativeUint8ClampedArray()
    {
    }

    /**
     * <p>Constructor for NativeUint8ClampedArray.</p>
     *
     * @param ab a {@link org.mozilla.javascript.typedarrays.NativeArrayBuffer} object.
     * @param off a int.
     * @param len a int.
     */
    public NativeUint8ClampedArray(NativeArrayBuffer ab, int off, int len)
    {
        super(ab, off, len, len);
    }

    /**
     * <p>Constructor for NativeUint8ClampedArray.</p>
     *
     * @param len a int.
     */
    public NativeUint8ClampedArray(int len)
    {
        this(new NativeArrayBuffer(len), 0, len);
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
        NativeUint8ClampedArray a = new NativeUint8ClampedArray();
        a.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /** {@inheritDoc} */
    @Override
    protected NativeUint8ClampedArray construct(NativeArrayBuffer ab, int off, int len)
    {
        return new NativeUint8ClampedArray(ab, off, len);
    }

    /** {@inheritDoc} */
    @Override
    public int getBytesPerElement()
    {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    protected NativeUint8ClampedArray realThis(Scriptable thisObj, IdFunctionObject f)
    {
        return ensureType(thisObj, NativeUint8ClampedArray.class, f);
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_get(int index)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        return ByteIo.readUint8(arrayBuffer.buffer, index + offset);
    }

    /** {@inheritDoc} */
    @Override
    protected Object js_set(int index, Object c)
    {
        if (checkIndex(index)) {
            return Undefined.instance;
        }
        int val = Conversions.toUint8Clamp(c);
        ByteIo.writeUint8(arrayBuffer.buffer, index + offset, val);
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Integer get(int i)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer)js_get(i);
    }

    /** {@inheritDoc} */
    @Override
    public Integer set(int i, Integer aByte)
    {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer)js_set(i, aByte);
    }
}
