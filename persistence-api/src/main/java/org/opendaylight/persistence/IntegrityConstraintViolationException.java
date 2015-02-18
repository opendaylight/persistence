/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence;

import javax.annotation.Nonnull;

/**
 * Thrown by the persistence provider to reflect an integrity constraint violation:
 * <p>
 * Documentation taken from: http://beginner-sql-tutorial.com/sql-integrity-constraints.htm
 * <ul>
 * <li><strong>Primary Key Integrity Constraint:</strong> This constraint defines a column or
 * combination of columns which uniquely identifies each row in the table. The key must not be null
 * and it must be unique</li>
 * <li><strong>Unique Integrity Constraint:</strong> This constraint ensures that a column or a
 * group of columns in each row have a distinct value. A column(s) can have a null value but the
 * values cannot be duplicated.</li>
 * <li><strong>Foreign Key or Referential Integrity:</strong> This constraint identifies any column
 * referencing the PRIMARY KEY in another table. It establishes a relationship between two columns
 * in the same table or between different tables. For a column to be defined as a Foreign Key, it
 * should be a defined as a Primary Key in the table which it is referring. One or more columns can
 * be defined as Foreign key.</li>
 * <li><strong>Not Null Constraint:</strong> This constraint ensures all rows in the table contain a
 * definite value for the column which is specified as not null. Which means a null value is not
 * allowed.</li>
 * </ul>
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class IntegrityConstraintViolationException extends PersistenceException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public IntegrityConstraintViolationException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *            {@link #getMessage()} method.
     */
    public IntegrityConstraintViolationException(@Nonnull String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *            {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()}
     *            method). (A <tt>null</tt> value is permitted, and indicates that the cause is
     *            nonexistent or unknown.)
     */
    public IntegrityConstraintViolationException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and
     * detail message of <tt>cause</tt>). This constructor is useful for exceptions that are little
     * more than wrappers for other throwables (for example,
     * {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()}
     *            method). (A <tt>null</tt> value is permitted, and indicates that the cause is
     *            nonexistent or unknown).
     */
    public IntegrityConstraintViolationException(@Nonnull Throwable cause) {
        super(cause);
    }
}
