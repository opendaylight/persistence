/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Page request.
 *
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int size;

    /**
     * Creates a page request.
     *
     * @param size page size or limit: Maximum number of data items per page
     * @throws IllegalArgumentException if {@code size} is less or equals than zero
     */
    public PageRequest(int size) {
        Preconditions.checkArgument(size > 0, "size must be greater than zero");
        this.size = size;
    }

    /**
     * Gets the page size or limit: Maximum number of data items per page.
     *
     * @return the page size
     */
    public int getSize() {
        return this.size;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.size));
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

        PageRequest other = (PageRequest)obj;

        if (this.size != other.size) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("size", this.size).toString();
    }
}
