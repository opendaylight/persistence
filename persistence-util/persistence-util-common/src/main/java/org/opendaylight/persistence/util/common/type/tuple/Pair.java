/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.tuple;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 2-Tuple.
 * <p>
 * Tuples are convenient data structures to use with self-described types. For example the tuple
 * Tuple&lt;Address, PhoneNumber, Email&gt; makes easy to know what each element represents; in the
 * other hand, the tuple Tuple&lt;String, String, Integer&gt; makes hard to figure out what each
 * element represents.
 * 
 * @param <T> Type for the first element of the tuple
 * @param <E> Type for the second element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Pair<T, E> extends UnaryTuple<T> {
    private static final long serialVersionUID = 1L;

    private E second;

    /**
     * Constructs a 2-tuple.
     * 
     * @param first First element in the tuple
     * @param second Second element in the tuple
     */
    protected Pair(@Nullable T first, @Nullable E second) {
        super(first);
        this.second = second;
    }

    /**
     * Creates a new 2-tuple using the given values.
     * 
     * @param <T> the type for the first element of the tuple
     * @param <E> the type for the second element of the tuple
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @return a new 2-tuple using the given values
     */
    public static <T, E> Pair<T, E> valueOf(@Nullable T first, @Nullable E second) {
        return new Pair<T, E>(first, second);
    }

    /**
     * Gets the second element of the tuple.
     *
     * @return The second element
     */
    public E getSecond() {
        return this.second;
    }

    /**
     * Sets the second element of the tuple.
     *
     * @param second The Second element
     */
    public void setSecond(@Nullable E second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFirst(), this.second);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>)obj;

        if (!Objects.equal(this.second, other.second)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", getFirst()).add("second", this.second).toString();
    }
}
