/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Serialized version of valuetype, however this class does not extends from
 * valuetype to avoid providing an accessible parameterless constructor: If a class that is
 * designed for inheritance is not {@link Serializable}, it may be impossible to write a
 * {@link Serializable} subclass. Specifically, it will be impossible if the superclass does not
 * provide an accessible parameterless constructor.
 * <P>
 * This class is immutable and so should subclasses.
 *
 * @param <E> type of the internal representation of the value type
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class SerializableValueType<E extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final E value;

    /**
     * Creates a new value type.
     *
     * @param value value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    protected SerializableValueType(E value) {
        this.value = Preconditions.checkNotNull(value, "value cannot be null");
    }

    /**
     * Gets the internal representation.
     *
     * @return internal representation
     */
    public E getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
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

        SerializableValueType<?> other = (SerializableValueType<?>) obj;

        if (!this.value.equals(other.value)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("value", this.value).toString();
    }

    /**
     * Converts a value type to its internal representation.
     *
     * @param valueType value type to convert to its internal representation
     * @return internal representation if {@code valueType} is not {@code null}, {@code null}
     * otherwise
     */
    public static <E extends Serializable> E toValue(SerializableValueType<E> valueType) {
        if (valueType == null) {
            return null;
        }
        return valueType.getValue();
    }
}

