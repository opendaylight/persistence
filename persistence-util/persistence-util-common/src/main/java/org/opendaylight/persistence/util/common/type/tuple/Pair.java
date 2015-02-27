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
 * @param <T1> Type for the first element of the tuple
 * @param <T2> Type for the second element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Pair<T1, T2> extends UnaryTuple<T1> {
    private static final long serialVersionUID = 1L;

    private T2 second;

    /**
     * Constructs a 2-tuple.
     * 
     * @param first First element in the tuple
     * @param second Second element in the tuple
     */
    protected Pair(@Nullable T1 first, @Nullable T2 second) {
        super(first);
        this.second = second;
    }

    /**
     * Creates a new 2-tuple using the given values.
     * 
     * @param <T1> the type for the first element of the tuple
     * @param <T2> the type for the second element of the tuple
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @return a new 2-tuple using the given values
     */
    public static <T1, T2> Pair<T1, T2> valueOf(@Nullable T1 first, @Nullable T2 second) {
        return new Pair<T1, T2>(first, second);
    }

    /**
     * Gets the second element of the tuple.
     *
     * @return The second element
     */
    public T2 getSecond() {
        return this.second;
    }

    /**
     * Sets the second element of the tuple.
     *
     * @param second The Second element
     */
    public void setSecond(@Nullable T2 second) {
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
