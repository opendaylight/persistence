/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.type;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.persistence.util.common.type.SerializableValueType;

import com.google.common.base.Preconditions;

/**
 * E-mail.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class Email extends SerializableValueType<String> {
    private static final long serialVersionUID = 1L;

    private static Pattern PATTERN = Pattern.compile("^[-_A-Za-z0-9]+(\\.[-_A-Za-z0-9]+)*@[-_A-Za-z0-9]+(\\.[-_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private Email(@Nonnull String value) {
        super(value);
        Preconditions.checkArgument(isValid(value), "Invalid format: " + value);
    }

    /**
     * Verifies whether a string represents a valid e-mail.
     *
     * @param value value to validate
     * @return {@code true} id {@code value} represents a valid e-mail, {@code false} otherwise
     */
    public static boolean isValid(@Nullable String value) {
        return value != null && PATTERN.matcher(value).matches();
    }

    /**
     * Constructs an e-mail form the string representation.
     *
     * @param value value
     * @return an e-mail
     */
    public static Email valueOf(@Nonnull String value) {
        return new Email(value);
    }
}
