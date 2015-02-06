/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.opendaylight.persistence.util.common.converter.ObjectToStringConverter;
import org.opendaylight.persistence.util.common.type.Property;

import javax.annotation.Nonnull;

/**
 * Mark based page request.
 *
 * @param <M> type of the mark.
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class MarkPageRequest<M> extends PageRequest {
    private static final long serialVersionUID = 1L;

    private final M mark;
    private final Navigation navigation;

    /**
     * Creates a request for the first page.
     *
     * @param size page size or limit: Maximum number of data items per page
     */
    public MarkPageRequest(int size) {
        this(null, Navigation.NEXT, size);
    }

    /**
     * Creates a page request.
     *
     * @param mark mark or record to take as reference
     * @param navigation page navigation type
     * @param size page size or limit: Maximum number of data items per page
     * @throws IllegalArgumentException if {@code size} is less or equals than zero
     */
    public MarkPageRequest(M mark, @Nonnull Navigation navigation, int size) {
        super(size);
        this.navigation = Preconditions.checkNotNull(navigation);
        this.mark = mark;
    }

    /**
     * Gets the mark.
     *
     * @return the mark
     */
    public M getMark() {
        return this.mark;
    }

    /**
     * Gets the page navigation type.
     *
     * @return the page navigation type
     */
    public Navigation getNavigation() {
        return this.navigation;
    }

    /**
     * Converts the page request to use a converted mark.
     *
     * @param convertedMark converted mark
     * @return a mark page request with a converted mark
     */
    public <T> MarkPageRequest<T> convert(T convertedMark) {
        return new MarkPageRequest<>(convertedMark, this.navigation, getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.mark.hashCode(), this.navigation.hashCode());
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

        MarkPageRequest<?> other = (MarkPageRequest<?>)obj;

        if(!Objects.equal(this.mark, other.mark)) {
            return false;
        }

        if (this.navigation != other.navigation) {
            return false;
        }

        if (getSize() != other.getSize()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).
                add("mark", this.mark).
                add("navigation", this.navigation).
                add("size", this.getSize()).toString();
    }

    /**
     * Page navigation type.
     */
    public enum Navigation {
        /**
         * Request the next page (Elements after the mark).
         */
        NEXT,
        /**
         * Requests the previous page (Elements previous to the mark).
         */
        PREVIOUS
    }
}

