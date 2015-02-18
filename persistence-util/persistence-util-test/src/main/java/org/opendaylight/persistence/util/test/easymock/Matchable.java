/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test.easymock;

import java.io.PrintStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Matchable property.
 * 
 * @param <T> type of the property value
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class Matchable<T> {
    private final String name;
    private final T expected;
    private final T actual;
    private final boolean matches;

    private Matchable(@Nonnull String name, @Nullable T expected, @Nullable T actual) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        this.name = name;
        this.expected = expected;
        this.actual = actual;
        this.matches = matches(expected, actual);
    }

    /**
     * Creates a matchable property.
     * 
     * @param name name of the property
     * @param expected expected value (it should override {@link Object#equals(Object)} method)
     * @param actual actual value (it should override {@link Object#equals(Object)} method)
     * @return a matchable property
     */
    public static <T> Matchable<T> valueOf(String name, T expected, T actual) {
        return new Matchable<T>(name, expected, actual);
    }

    /**
     * Verifies whether this matchable property actually matches.
     * 
     * @return {@code true} if the property's value matches the expected, {@code false} otherwise
     */
    public boolean matches() {
        return this.matches;
    }

    void printMismatch(PrintStream stream) {
        if (!this.matches) {
            stream.println("Mismatch on : " + this.name);
            stream.println("expected: " + this.expected);
            stream.println("Actual: " + this.actual);
        }
    }

    private static <T> boolean matches(T expected, T actual) {
        if (expected == null) {
            if (actual != null) {
                return false;
            }
        }
        else if (!expected.equals(actual)) {
            return false;
        }
        return true;
    }
}
