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
 * 4-Tuple.
 * <p>
 * Tuples are convenient data structures to use with self-described types. For example the tuple
 * Tuple&lt;Address, PhoneNumber, Email&gt; makes easy to know what each element represents; in the
 * other hand, the tuple Tuple&lt;String, String, Integer&gt; makes hard to figure out what each
 * element represents.
 * 
 * @param <T1> Type for the first element of the tuple
 * @param <T2> Type for the second element of the tuple
 * @param <T3> Type for the third element of the tuple
 * @param <T4> Type for the fourth element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Quadruplet<T1, T2, T3, T4> extends Triplet<T1, T2, T3> {
    private static final long serialVersionUID = 1L;

    private T4 fourth;

    /**
     * Constructs a 4-tuple.
     * 
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     * @param fourth Fourth element in the tuple
     */
    protected Quadruplet(@Nullable T1 first, @Nullable T2 second, @Nullable T3 third, @Nullable T4 fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    /**
     * Creates a new 4-tuple using the given values.
     * 
     * @param <T1> the type for the first element of the tuple
     * @param <T2> the type for the second element of the tuple
     * @param <T3> the type for the third element of the tuple
     * @param <T4> the type for the fourth element of the tuple
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     * @param fourth Fourth element in the tuple
     * @return a new 4-tuple using the given values
     */
    public static <T1, T2, T3, T4> Quadruplet<T1, T2, T3, T4> valueOf(@Nullable T1 first, @Nullable T2 second,
            @Nullable T3 third, @Nullable T4 fourth) {
        return new Quadruplet<T1, T2, T3, T4>(first, second, third, fourth);
    }

    /**
     * Gets the fourth element of the tuple.
     * 
     * @return The fourth element
     */
    public T4 getFourth() {
        return this.fourth;
    }

    /**
     * Sets the fourth element of the tuple.
     * 
     * @param fourth The fourth element
     */
    public void setFourth(@Nullable T4 fourth) {
        this.fourth = fourth;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFirst(), getSecond(), getThird(), this.fourth);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Quadruplet<?, ?, ?, ?> other = (Quadruplet<?, ?, ?, ?>) obj;

        if (!Objects.equal(this.fourth, other.fourth)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", getFirst()).add("second", getSecond())
                .add("third", getThird()).add("fourth", this.fourth).toString();
    }
}
