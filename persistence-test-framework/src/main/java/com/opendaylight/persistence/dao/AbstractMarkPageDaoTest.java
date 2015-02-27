/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.opendaylight.persistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Assume;
import org.junit.Test;
import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.MarkPageDao;
import org.opendaylight.persistence.util.common.type.page.MarkPage;
import org.opendaylight.persistence.util.common.type.page.MarkPageRequest;
import org.opendaylight.persistence.util.common.type.tuple.UnaryTuple;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Integration test for {@link MarkPageDao} implementations.
 * 
 * @param <I> type of the identifiable object's id
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <F> type of the associated filter
 * @param <S> type of the associated sort attribute or sort key used to construct sort
 *            specifications
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @param <D> type of the DAO to test
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class AbstractMarkPageDaoTest<I extends Serializable, T extends Identifiable<I>, F, S, C, D extends MarkPageDao<I, T, F, S, C>>
        extends AbstractDaoTest<I, T, F, S, C, D> {

    /**
     * Creates a new DAO integration test.
     * 
     * @param dataStore data store
     */
    public AbstractMarkPageDaoTest(@Nonnull DataStore<C> dataStore) {
        super(dataStore);
    }

    /**
     * @throws Exception if any errors occur during execution
     */
    @Test
    public void testPagedFind() throws Exception {
        List<SearchCase<T, F, S>> searchCases = getSearchCases();
        Assume.assumeNotNull(searchCases);

        for (final SearchCase<T, F, S> searchCase : searchCases) {
            // Persists the search space

            final Map<I, StoredObject<T>> persistedSearchSpace = store(searchCase.getSearchSpace());

            // Performs paged find

            // Try different page sizes

            int totalRecords = searchCase.getExpectedResult().size();

            for (int size = 1; size <= totalRecords; size++) {
                int totalPages = totalRecords / size;

                // handle extra non-full page at the end
                if (totalPages * size < totalRecords) {
                    totalPages = totalPages + 1;
                }

                // Checks each page

                /*
                 * Search result will contain the aggregated records from all pages to compare at
                 * the end
                 */
                List<T> aggregatedResult = new ArrayList<T>(totalRecords);

                MarkPage<T> page = null;
                final UnaryTuple<MarkPageRequest<T>> pageRequest = UnaryTuple.valueOf(new MarkPageRequest<T>(size));

                do {

                    page = execute(new DaoQuery<MarkPage<T>>() {

                        @Override
                        protected MarkPage<T> execute(D dao, C context) throws PersistenceException {
                            return dao.find(searchCase.getFilter(), searchCase.getSort(),
                                    pageRequest.getFirst(), context);
                        }
                    });

                    aggregatedResult.addAll(page.getData());

                    pageRequest.setFirst(page.getNextPageRequest());

                }
                while (!page.getData().isEmpty());

                assertSearch(searchCase.getExpectedResult(), aggregatedResult, persistedSearchSpace,
                        searchCase.isOrdered(), getMessage(searchCase));
            }

            clear();
        }
    }
}
