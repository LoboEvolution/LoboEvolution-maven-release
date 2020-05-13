/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

/**
 * AST node for the '.' operator.  Node type is {@link org.mozilla.javascript.Token#GETPROP}.
 *
 * @author utente
 * @version $Id: $Id
 */
public class PropertyGet extends InfixExpression {

    {
        type = Token.GETPROP;
    }

    /**
     * <p>Constructor for PropertyGet.</p>
     */
    public PropertyGet() {
    }

    /**
     * <p>Constructor for PropertyGet.</p>
     *
     * @param pos a int.
     */
    public PropertyGet(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for PropertyGet.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public PropertyGet(int pos, int len) {
        super(pos, len);
    }

    /**
     * <p>Constructor for PropertyGet.</p>
     *
     * @param pos a int.
     * @param len a int.
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param property a {@link org.mozilla.javascript.ast.Name} object.
     */
    public PropertyGet(int pos, int len, AstNode target, Name property) {
        super(pos, len, target, property);
    }

    /**
     * Constructor.  Updates bounds to include left ({@code target}) and
     * right ({@code property}) nodes.
     *
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param property a {@link org.mozilla.javascript.ast.Name} object.
     */
    public PropertyGet(AstNode target, Name property) {
        super(target, property);
    }

    /**
     * <p>Constructor for PropertyGet.</p>
     *
     * @param target a {@link org.mozilla.javascript.ast.AstNode} object.
     * @param property a {@link org.mozilla.javascript.ast.Name} object.
     * @param dotPosition a int.
     */
    public PropertyGet(AstNode target, Name property, int dotPosition) {
        super(Token.GETPROP, target, property, dotPosition);
    }

    /**
     * Returns the object on which the property is being fetched.
     * Should never be {@code null}.
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getTarget() {
        return getLeft();
    }

    /**
     * Sets target object, and sets its parent to this node.
     *
     * @param target expression evaluating to the object upon which
     * to do the property lookup
     * @throws java.lang.IllegalArgumentException} if {@code target} is {@code null}
     */
    public void setTarget(AstNode target) {
        setLeft(target);
    }

    /**
     * Returns the property being accessed.
     *
     * @return a {@link org.mozilla.javascript.ast.Name} object.
     */
    public Name getProperty() {
        return (Name)getRight();
    }

    /**
     * Sets the property being accessed, and sets its parent to this node.
     *
     * @throws java.lang.IllegalArgumentException} if {@code property} is {@code null}
     * @param property a {@link org.mozilla.javascript.ast.Name} object.
     */
    public void setProperty(Name property) {
        setRight(property);
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append(getLeft().toSource(0));
        sb.append(".");
        sb.append(getRight().toSource(0));
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, the target expression, and the property name.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            getTarget().visit(v);
            getProperty().visit(v);
        }
    }
}