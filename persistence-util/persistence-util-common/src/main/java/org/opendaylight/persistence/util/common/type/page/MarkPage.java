/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import org.opendaylight.persistence.util.common.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mark based data page.
 *
 * @param <D> type of the data
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class MarkPage<D> extends Page<MarkPageRequest<D>, D> {
    private static final long serialVersionUID = 1L;

    private static final MarkPage EMPTY_PAGE = new MarkPage(new MarkPageRequest(1), Collections.emptyList());

    /**
     * Creates a data page.
     *
     * @param pageRequest request that generated this page
     * @param data page's data
     */
    public MarkPage(MarkPageRequest<D> pageRequest, List<D> data) {
        super(pageRequest, data);
    }

    /**
     * Returns the empty page (immutable).
     *
     * @return an empty page
     */
    @SuppressWarnings({"cast", "unchecked"})
    public static final <T> MarkPage<T> emptyPage() {
        return (MarkPage<T>)EMPTY_PAGE;
    }

    /**
     * Converts a page to a different data type.
     *
     * @param converter converter
     * @return a page with the new data type
     */
    public <T> MarkPage<T> convert(Converter<D, T> converter) {
        List<T> targetItems = new ArrayList<T>(getData().size());
        for (D item : getData()) {
            targetItems.add(converter.convert(item));
        }

        T targetMark = getRequest().getMark() != null ? converter.convert(getRequest().getMark()) : null;
        MarkPageRequest<T> targetRequest = new MarkPageRequest<T>(targetMark, getRequest().getNavigation(),
                getRequest().getSize());

        return new MarkPage<T>(targetRequest, targetItems);
    }

    /**
     * Creates a request for the next page.
     *
     * @return a request for the next page if there is a page, {@code null} otherwise
     */
    public MarkPageRequest<D> getNextPageRequest() {
        MarkPageRequest<D> request = null;

        if (!isEmpty()) {
            request = new MarkPageRequest<D>(getData().get(getData().size() - 1), MarkPageRequest.Navigation.NEXT,
                    getRequest().getSize());
        }

        return request;
    }

    /**
     * Creates a request for the previous page.
     *
     * @return a request for the previous page if there is a page, {@code null} otherwise
     */
    public MarkPageRequest<D> getPreviousPageRequest() {
        MarkPageRequest<D> request = null;

        if (!isEmpty()) {
            request = new MarkPageRequest<D>(getData().get(0), MarkPageRequest.Navigation.PREVIOUS,
                    getRequest().getSize());
        }

        return request;
    }
}
