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

import com.google.common.base.Preconditions;

/**
 * Username.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class Username extends SerializableValueType<String> {
    private static final long serialVersionUID = 1L;

    private Username(@Nonnull String value) {
        super(value);
        Preconditions.checkArgument(!value.isEmpty(), "value cannot be empty");
    }

    /**
     * Creates a username.
     * 
     * @param value username value
     * @return a username
     */
    public static Username valueOf(@Nonnull String value) {
        return new Username(value);
    }
}
