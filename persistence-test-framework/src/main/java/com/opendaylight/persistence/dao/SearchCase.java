/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.opendaylight.persistence.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.persistence.util.common.type.Sort;

import com.google.common.base.MoreObjects;

/**
 * Search case.
 * 
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <F> type of the associated filter
 * @param <S> type of the associated sort attribute or sort key used to construct sort
 *            specifications
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class SearchCase<T, F, S> {
    private List<T> searchSpace;
    private F filter;
    private List<Sort<S>> sort;
    private List<T> expectedResult;

    private SearchCase(@Nonnull List<T> searchSpace, @Nonnull F filter, @Nullable List<Sort<S>> sort,
            @Nonnull List<T> expectedResult) {
        this.searchSpace = new ArrayList<T>(searchSpace);
        this.filter = filter;
        this.sort = sort;
        if (expectedResult != null) {
            this.expectedResult = Collections.unmodifiableList(new ArrayList<T>(expectedResult));
        }
        else {
            this.expectedResult = Collections.emptyList();
        }
    }

    /**
     * Creates a search case.
     * 
     * @param searchSpace a collection of identifiable objects to use in a test case. They will
     *            represent the content of the table for the test case. These identifiable objects
     *            must be a valid storable collection since they will be persisted all together. So
     *            if an attribute is unique (unique column in the database table), such attribute
     *            must be is unique for the search space. The search space can be considered as the
     *            entire table content, so it is enough to make sure unique fields are unique just
     *            for the search space; These objects will be removed from the database after the
     *            test.
     * @param filter filter to apply
     * @param sort sort specification to apply
     * @param expectedResult expected search result. This must be a subset of {@code searchSpace}
     *            using the same objects references.
     * @return search case
     */
    public static <T, F, S> SearchCase<T, F, S> forCase(@Nonnull List<T> searchSpace, @Nonnull F filter,
            @Nullable List<Sort<S>> sort, @Nonnull List<T> expectedResult) {
        return new SearchCase<T, F, S>(searchSpace, filter, sort, expectedResult);
    }

    /**
     * Creates a search case.
     * 
     * @param searchSpace search space
     * @param filter filter to apply
     * @param sort sort specification to apply
     * @param expectedResult expected search result. This must be a subset of {@code searchSpace}
     *            using the same objects references.
     * @return search case
     */
    @SafeVarargs
    public static <T, F, S> SearchCase<T, F, S> forCase(@Nonnull List<T> searchSpace, @Nonnull F filter,
            @Nullable List<Sort<S>> sort, @Nonnull T... expectedResult) {
        return forCase(searchSpace, filter, sort, Arrays.asList(expectedResult));
    }

    /**
     * Gets the search space.
     * 
     * @return the search space
     */
    public List<T> getSearchSpace() {
        return this.searchSpace;
    }

    /**
     * Gets the filter.
     * 
     * @return the filter
     */
    public F getFilter() {
        return this.filter;
    }

    /**
     * Gets the sort specification.
     * 
     * @return the sort specification
     */
    public List<Sort<S>> getSort() {
        return this.sort;
    }

    /**
     * Gets the expected result.
     * 
     * @return the expected result
     */
    public List<T> getExpectedResult() {
        return this.expectedResult;
    }

    /**
     * Verifies if order has been considered in this search case.
     * 
     * @return {@code true} if the expected result is ordered, {@code false} otherwise
     */
    public boolean isOrdered() {
        return this.sort != null && !this.sort.isEmpty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("filter", this.filter).add("sort", this.sort)
                .add("expectedResult", this.expectedResult).add("searchSpace", this.searchSpace).toString();
    }
}
