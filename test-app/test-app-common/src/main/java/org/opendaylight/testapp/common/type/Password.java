/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Password.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class Password {

    /*
     * This class should not be serializable for security reasons.
     */

    private String value;

    private Password(@Nonnull String value) {
        this.value = Preconditions.checkNotNull(value, "value");
    }

    /**
     * Creates a password.
     * 
     * @param value password value
     * @return a password
     */
    public static Password valueOf(@Nullable String value) {
        if (value == null) {
            return null;
        }
        return new Password(value);
    }

    /**
     * Gets the password value.
     * 
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Password)) {
            return false;
        }

        Password other = (Password) obj;

        if (!Objects.equal(this.value, other.value)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("value", "****").toString();
    }
}
