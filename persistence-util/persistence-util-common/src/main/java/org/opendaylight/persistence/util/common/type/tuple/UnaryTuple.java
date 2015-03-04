/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.tuple;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 1-Tuple.
 * <p>
 * In mathematics and computer science a tuple captures the intuitive notion of an ordered list of
 * elements. Depending on the mathematical foundation chosen, the formal notion differs slightly. In
 * set theory, an (ordered) n-tuple is a sequence (or ordered list) of n elements, where n is a
 * positive integer.
 * <p>
 * The main properties that distinguish a tuple from, for example, a set are that
 * <ol>
 * <li>It can contain an object more than once</li>
 * <li>The objects appear in a certain order</li>
 * <li>It has finite size</li>
 * </ol>
 * <p>
 * Tuples are convenient data structures to use with self-described types. For example the tuple
 * Tuple&lt;Address, PhoneNumber, Email&gt; makes easy to know what each element represents; in the
 * other hand, the tuple Tuple&lt;String, String, Integer&gt; makes hard to figure out what each
 * element represents.
 * 
 * @param <T> Type for the first element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class UnaryTuple<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T first;

    /**
     * Constructs a 1-tuple.
     * 
     * @param first First element in the tuple
     */
    protected UnaryTuple(@Nullable T first) {
        this.first = first;
    }

    /**
     * Creates a new 1-tuple using the given values.
     * 
     * @param <T> the type for the first element of the tuple
     * @param first First element in the tuple
     * @return a new 1-tuple using the given values
     */
    public static <T> UnaryTuple<T> valueOf(@Nullable T first) {
        return new UnaryTuple<T>(first);
    }

    /**
     * Gets the first element of the tuple.
     *
     * @return The first element
     */
    public T getFirst() {
        return this.first;
    }

    /**
     * Sets the first element of the tuple.
     *
     * @param first The first element
     */
    public void setFirst(@Nullable T first) {
        this.first = first;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.first);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        UnaryTuple<?> other = (UnaryTuple<?>)obj;

        if (!Objects.equal(this.first, other.first)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", this.first).toString();
    }
}
