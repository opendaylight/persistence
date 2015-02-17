/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Offset based page request.
 *
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class OffsetPageRequest extends PageRequest {
    private static final long serialVersionUID = 1L;

    private final long offset;

    /**
     * Creates a request for the first page.
     *
     * @param size page size or limit: Maximum number of data items per page
     */
    public OffsetPageRequest(int size) {
        this(0, size);
    }

    /**
     * Creates a page request.
     *
     * @param offset index of the first data item to return
     * @param size page size or limit: Maximum number of data items per page
     * @throws IllegalArgumentException if {@code pageIndex} is less than zero
     */
    public OffsetPageRequest(long offset, int size) {
        super(size);
        Preconditions.checkArgument(offset >= 0, "offset must be greater or equals to zero");
        this.offset = offset;
    }

    /**
     * Gets the index of the first data item to return.
     *
     * @return the offset
     */
    public long getOffset() {
        return this.offset;
    }

    /**
     * Calculates the page index based on the offset and limit.
     *
     * @return the page index
     */
    public int getPageIndex() {
        long pageIndex = this.offset / getSize();

        if (this.offset % getSize() != 0) {
            pageIndex++;
        }

        return (int)pageIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.offset), Integer.valueOf(this.getSize()));
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

        OffsetPageRequest other = (OffsetPageRequest)obj;

        if (this.offset != other.offset) {
            return false;
        }

        if (getSize() != other.getSize()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("offset", this.offset).
                add("size", this.getSize()).toString();
    }
}
