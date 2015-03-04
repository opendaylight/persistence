/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.type;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.util.common.type.SerializableValueType;

/**
 * Serial number.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class SerialNumber extends SerializableValueType<String> {

    private static final long serialVersionUID = 1L;

    private SerialNumber(@Nonnull String value) throws IllegalArgumentException {
        super(value);
    }

    /**
     * Creates an serial number from the given value.
     * 
     * @param value serial number's value
     * @return a serial number
     * @throws IllegalArgumentException if {@code value} does not represent a serial number
     */
    public static SerialNumber valueOf(@Nonnull String value) throws IllegalArgumentException {
        return new SerialNumber(value);
    }
}
