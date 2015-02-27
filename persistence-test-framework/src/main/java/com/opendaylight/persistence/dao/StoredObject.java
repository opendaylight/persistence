/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.opendaylight.persistence.dao;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

/**
 * Stored object.
 * 
 * @param <T> type of the identifiable object (object to store in the data store)
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class StoredObject<T> {

    private final T original;
    private final T stored;

    private StoredObject(@Nonnull T original, @Nonnull T stored) {
        this.original = original;
        this.stored = stored;
    }

    /**
     * Creates a stored object
     * 
     * @param original object used to store
     * @param stored the object as it is in the data store
     * @return a stored object
     */
    public static <T> StoredObject<T> valueOf(@Nonnull T original, @Nonnull T stored) {
        return new StoredObject<T>(original, stored);
    }

    /**
     * Gets the object used to store.
     * 
     * @return the original
     */
    public T getOriginal() {
        return this.original;
    }

    /**
     * Gets the stored the object as it is in the data store.
     * 
     * @return the stored
     */
    public T getStored() {
        return this.stored;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("original", this.original).add("stored", this.stored).toString();
    }
}
