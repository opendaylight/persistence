/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common;

import com.google.common.base.Objects;

/**
 * Utility methods.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class Util {

    private Util() {

    }

    /**
     * Verifies id the two objects are equals considering {@code null}.
     * 
     * @param obj1
     *            one of the objects to compare
     * @param obj2
     *            the other object to compare
     * @return {@code true} if both objects are either {@code null} or equals (as stated in
     *         {@link Object#equals(Object)}), {@code false} otherwise
     */
    public static <T> boolean equals(T obj1, T obj2) {
        return Objects.equal(obj1, obj2);
    }
}
