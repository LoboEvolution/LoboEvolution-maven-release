/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.lang.reflect.Array;

/**
 * This class reflects Java arrays into the JavaScript environment.
 *
 * Author Mike Shaver
 * @see NativeJavaClass
 * @see NativeJavaObject
 * @see NativeJavaPackage
 *
 */
public class NativeJavaArray
    extends NativeJavaObject
    implements SymbolScriptable
{
    private static final long serialVersionUID = -924022554283675333L;

    /** {@inheritDoc} */
    @Override
    public String getClassName() {
        return "JavaArray";
    }

    /**
     * <p>wrap.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param array a {@link java.lang.Object} object.
     * @return a {@link org.mozilla.javascript.NativeJavaArray} object.
     */
    public static NativeJavaArray wrap(Scriptable scope, Object array) {
        return new NativeJavaArray(scope, array);
    }

    /** {@inheritDoc} */
    @Override
    public Object unwrap() {
        return array;
    }

    /**
     * <p>Constructor for NativeJavaArray.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param array a {@link java.lang.Object} object.
     */
    public NativeJavaArray(Scriptable scope, Object array) {
        super(scope, null, ScriptRuntime.ObjectClass);
        Class<?> cl = array.getClass();
        if (!cl.isArray()) {
            throw new RuntimeException("Array expected");
        }
        this.array = array;
        this.length = Array.getLength(array);
        this.cls = cl.getComponentType();
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(String id, Scriptable start) {
        return id.equals("length") || super.has(id, start);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(int index, Scriptable start) {
        return 0 <= index && index < length;
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(Symbol key, Scriptable start) {
        return SymbolKey.IS_CONCAT_SPREADABLE.equals(key);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(String id, Scriptable start) {
        if (id.equals("length"))
            return Integer.valueOf(length);
        Object result = super.get(id, start);
        if (result == NOT_FOUND &&
            !ScriptableObject.hasProperty(getPrototype(), id))
        {
            throw Context.reportRuntimeErrorById(
                "msg.java.member.not.found", array.getClass().getName(), id);
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(int index, Scriptable start) {
        if (0 <= index && index < length) {
            Context cx = Context.getContext();
            Object obj = Array.get(array, index);
            return cx.getWrapFactory().wrap(cx, this, obj, cls);
        }
        return Undefined.instance;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Symbol key, Scriptable start) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(key)) {
            return Boolean.TRUE;
        }
        return Scriptable.NOT_FOUND;
    }

    /** {@inheritDoc} */
    @Override
    public void put(String id, Scriptable start, Object value) {
        // Ignore assignments to "length"--it's readonly.
        if (!id.equals("length"))
            throw Context.reportRuntimeErrorById(
                "msg.java.array.member.not.found", id);
    }

    /** {@inheritDoc} */
    @Override
    public void put(int index, Scriptable start, Object value) {
        if (0 <= index && index < length) {
            Array.set(array, index, Context.jsToJava(value, cls));
        }
        else {
            throw Context.reportRuntimeErrorById(
                "msg.java.array.index.out.of.bounds", String.valueOf(index),
                String.valueOf(length - 1));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Symbol key) {
        // All symbols are read-only
    }

    /** {@inheritDoc} */
    @Override
    public Object getDefaultValue(Class<?> hint) {
        if (hint == null || hint == ScriptRuntime.StringClass)
            return array.toString();
        if (hint == ScriptRuntime.BooleanClass)
            return Boolean.TRUE;
        if (hint == ScriptRuntime.NumberClass)
            return ScriptRuntime.NaNobj;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Object[] getIds() {
        Object[] result = new Object[length];
        int i = length;
        while (--i >= 0)
            result[i] = Integer.valueOf(i);
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasInstance(Scriptable value) {
        if (!(value instanceof Wrapper))
            return false;
        Object instance = ((Wrapper)value).unwrap();
        return cls.isInstance(instance);
    }

    /** {@inheritDoc} */
    @Override
    public Scriptable getPrototype() {
        if (prototype == null) {
            prototype =
                ScriptableObject.getArrayPrototype(this.getParentScope());
        }
        return prototype;
    }

    Object array;
    int length;
    Class<?> cls;
}
