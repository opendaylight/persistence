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
 * @param <T> Type for the first element of the tuple
 * @param <E> Type for the second element of the tuple
 * @param <S> Type for the third element of the tuple
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Triplet<T, E, S> extends Pair<T, E> {
    private static final long serialVersionUID = 1L;

    private S third;

    /**
     * Constructs a 3-tuple.
     * 
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     */
    protected Triplet(@Nullable T first, @Nullable E second, @Nullable S third) {
        super(first, second);
        this.third = third;
    }

    /**
     * Creates a new 3-tuple using the given values.
     * 
     * @param <T> the type for the first element of the tuple
     * @param <E> the type for the second element of the tuple
     * @param <S> the type for the third element of the tuple
     * @param first First element in the tuple
     * @param second Second element in the tuple
     * @param third Third element in the tuple
     * @return a new 3-tuple using the given values
     */
    public static <T, E, S> Triplet<T, E, S> valueOf(@Nullable T first, @Nullable E second, @Nullable S third) {
        return new Triplet<T, E, S>(first, second, third);
    }

    /**
     * Gets the third element of the tuple.
     *
     * @return The third element.
     */
    public S getThird() {
        return this.third;
    }

    /**
     * Sets the third element of the tuple.
     *
     * @param third The third element
     */
    public void setThird(@Nullable S third) {
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
