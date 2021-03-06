/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * <p>Abstract ES6Iterator class.</p>
 *
 *
 *
 */
public abstract class ES6Iterator extends IdScriptableObject {

    private static final long serialVersionUID = 2438373029140003950L;

    /**
     * <p>init.</p>
     *
     * @param scope a {@link org.mozilla.javascript.ScriptableObject} object.
     * @param sealed a boolean.
     * @param prototype a {@link org.mozilla.javascript.IdScriptableObject} object.
     * @param tag a {@link java.lang.String} object.
     */
    protected static void init(ScriptableObject scope, boolean sealed, IdScriptableObject prototype, String tag) {
        if (scope != null) {
            prototype.setParentScope(scope);
            prototype.setPrototype(getObjectPrototype(scope));
        }
        prototype.activatePrototypeMap(MAX_PROTOTYPE_ID);
        if (sealed) {
            prototype.sealObject();
        }

        // Need to access Iterator prototype when constructing
        // Iterator instances, but don't have a iterator constructor
        // to use to find the prototype. Use the "associateValue"
        // approach instead.
        if (scope != null) {
            scope.associateValue(tag, prototype);
        }
    }

    protected boolean exhausted = false;
    private String tag;

    /**
     * <p>Constructor for ES6Iterator.</p>
     */
    protected ES6Iterator() {}

    /**
     * <p>Constructor for ES6Iterator.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param tag a {@link java.lang.String} object.
     */
    protected ES6Iterator(Scriptable scope, String tag) {
        // Set parent and prototype properties. Since we don't have a
        // "Iterator" constructor in the top scope, we stash the
        // prototype in the top scope's associated value.
        this.tag = tag;
        Scriptable top = ScriptableObject.getTopLevelScope(scope);
        this.setParentScope(top);
        IdScriptableObject prototype = (IdScriptableObject)
            ScriptableObject.getTopScopeValue(top, tag);
        setPrototype(prototype);
    }

    /** {@inheritDoc} */
    @Override
    protected void initPrototypeId(int id)
    {
        switch (id) {
            case Id_next:
                initPrototypeMethod(getTag(), id, NEXT_METHOD, 0);
                return;
            case SymbolId_iterator:
                initPrototypeMethod(getTag(), id, SymbolKey.ITERATOR, "[Symbol.iterator]", DONTENUM | READONLY);
                return;
            case SymbolId_toStringTag:
                initPrototypeValue(SymbolId_toStringTag, SymbolKey.TO_STRING_TAG, getClassName(), DONTENUM | READONLY);
                return;
            default: throw new IllegalArgumentException(String.valueOf(id));
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(getTag())) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();

        ES6Iterator iterator = ensureType(thisObj, ES6Iterator.class, f);

        switch (id) {
        case Id_next:
            return iterator.next(cx, scope);
        case SymbolId_iterator:
            return iterator;
        default:
            throw new IllegalArgumentException(String.valueOf(id));
        }
    }

    /** {@inheritDoc} */
    @Override
    protected int findPrototypeId(Symbol k) {
        if (SymbolKey.ITERATOR.equals(k)) {
            return SymbolId_iterator;
        } else if (SymbolKey.TO_STRING_TAG.equals(k)) {
            return SymbolId_toStringTag;
        }
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    protected int findPrototypeId(String s) {
        if (NEXT_METHOD.equals(s)) {
            return Id_next;
        }
        return 0;
    }

    /**
     * <p>isDone.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a boolean.
     */
    abstract protected boolean isDone(Context cx, Scriptable scope);

    /**
     * <p>nextValue.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    abstract protected Object nextValue(Context cx, Scriptable scope);

    /**
     * <p>next.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    protected Object next(Context cx, Scriptable scope) {
        Object value = Undefined.instance;
        boolean done = isDone(cx, scope) || this.exhausted;
        if (!done) {
            value = nextValue(cx, scope);
        } else {
            this.exhausted = true;
        }
        return makeIteratorResult(cx, scope, Boolean.valueOf(done), value);
    }

    /**
     * <p>Getter for the field <code>tag</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected String getTag() {
        return tag;
    }

    // 25.1.1.3 The IteratorResult Interface
    static Scriptable makeIteratorResult(Context cx, Scriptable scope, Boolean done) {
        return makeIteratorResult(cx, scope, done, Undefined.instance);
    }

    static Scriptable makeIteratorResult(Context cx, Scriptable scope, Boolean done, Object value) {
        final Scriptable iteratorResult = cx.newObject(scope);
        ScriptableObject.putProperty(iteratorResult, VALUE_PROPERTY, value);
        ScriptableObject.putProperty(iteratorResult, DONE_PROPERTY, done);
        return iteratorResult;
    }

    private static final int
        Id_next              = 1,
        SymbolId_iterator    = 2,
        SymbolId_toStringTag = 3,
        MAX_PROTOTYPE_ID     = SymbolId_toStringTag;

    /** Constant <code>NEXT_METHOD="next"</code> */
    public static final String NEXT_METHOD = "next";
    /** Constant <code>DONE_PROPERTY="done"</code> */
    public static final String DONE_PROPERTY = "done";
    /** Constant <code>RETURN_PROPERTY="return"</code> */
    public static final String RETURN_PROPERTY = "return";
    /** Constant <code>VALUE_PROPERTY="value"</code> */
    public static final String VALUE_PROPERTY = "value";
    /** Constant <code>RETURN_METHOD="return"</code> */
    public static final String RETURN_METHOD = "return";
}
