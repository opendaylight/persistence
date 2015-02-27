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
 * 3-Tuple.
 * <p>
 * Tuples are convenient data structures to use with self-described types. For example the tuple
 * Tuple&lt;Address, PhoneNumber, Email&gt; makes easy to know what each element represents; in the
 * other hand, the tuple Tuple&lt;String, String, Integer&gt; makes hard to figure out what each
 * element represents.
 * 
 * @param <T1> Type for the first element of the tuple
 * @param <T2> Type for the second element of the tuple
 * @param <T3> Type for the third element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Triplet<T1, T2, T3> extends Pair<T1, T2> {
    private static final long serialVersionUID = 1L;

    private T3 third;

    /**
     * Constructs a 3-tuple.
     * 
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     */
    protected Triplet(@Nullable T1 first, @Nullable T2 second, @Nullable T3 third) {
        super(first, second);
        this.third = third;
    }

    /**
     * Creates a new 3-tuple using the given values.
     * 
     * @param <T1> the type for the first element of the tuple
     * @param <T2> the type for the second element of the tuple
     * @param <T3> the type for the third element of the tuple
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     * @return a new 3-tuple using the given values
     */
    public static <T1, T2, T3> Triplet<T1, T2, T3> valueOf(@Nullable T1 first, @Nullable T2 second, @Nullable T3 third) {
        return new Triplet<T1, T2, T3>(first, second, third);
    }

    /**
     * Gets the third element of the tuple.
     *
     * @return The third element.
     */
    public T3 getThird() {
        return this.third;
    }

    /**
     * Sets the third element of the tuple.
     *
     * @param third The third element
     */
    public void setThird(@Nullable T3 third) {
        this.third = third;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFirst(), getSecond(), this.third);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Triplet<?, ?, ?> other = (Triplet<?, ?, ?>)obj;

        if (!Objects.equal(this.third, other.third)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", getFirst()).add("second", getSecond())
                .add("third", this.third).toString();
    }
}
