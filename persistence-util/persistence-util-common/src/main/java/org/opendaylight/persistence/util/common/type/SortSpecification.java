/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.opendaylight.persistence.util.common.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sort specification.
 *
 * @param <T> type of the attribute to sort by
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class SortSpecification<T> {
    private static final int initialCapacity = 2;
    private List<SortComponent<T>> components;

    /**
     * Creates a sort specification.
     */
    public SortSpecification() {
        this.components = new ArrayList<>(initialCapacity);
    }

    /**
     * Appends a sort by component.
     *
     * @param attribute attribute to sort by
     * @param sortOrder sort order
     */
    public void addSortComponent(T attribute, SortOrder sortOrder) {
        this.components.add(new SortComponent<>(attribute, sortOrder));
    }

    /**
     * Gets the specification's sort components.
     *
     * @return the specification's sort components
     */
    public List<SortComponent<T>> getSortComponents() {
        return ImmutableList.copyOf(this.components);
    }

    /**
     * Converts this sort specification to a different 'sort by' data type.
     *
     * @param converter converter
     * @return a sort specification with the new data type
     */
    public <D> SortSpecification<D> convert(Converter<T, D> converter) {
        SortSpecification<D> converted = new SortSpecification<>();
        for (SortComponent<T> component : this.components) {
            converted.addSortComponent(converter.convert(component.getSortBy()), component.getSortOrder());
        }
        return converted;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).
                add("components", this.components).toString();
    }

    /**
     * Sort component.
     *
     * @param <T> type of the attribute to sort by
     */
    public static final class SortComponent<T> {
        private T sortBy;
        private SortOrder sortOrder;

        private SortComponent(T sortBy, SortOrder sortOrder) {
            this.sortBy = sortBy;
            this.sortOrder = sortOrder;
        }

        /**
         * Gets the attribute to sort by.
         *
         * @return the attribute to sort by
         */
        public T getSortBy() {
            return this.sortBy;
        }

        /**
         * Gets the sort order.
         *
         * @return the sort order
         */
        public SortOrder getSortOrder() {
            return this.sortOrder;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this.getClass()).
                    add("sortBy", getSortBy()).
                    add("sortOrder", getSortOrder()).
                    toString();
        }
    }
}

