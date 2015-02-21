/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.EqualityTester;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class SortTest {

    @Test
    public void testConstruction() {
        Sort<String> sort = Sort.ascending("attr");
        Assert.assertEquals("attr", sort.by());
        Assert.assertEquals(SortOrder.ASCENDING, sort.order());

        sort = Sort.descending("attr");
        Assert.assertEquals("attr", sort.by());
        Assert.assertEquals(SortOrder.DESCENDING, sort.order());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidAscendingConstruction() {
        Sort.ascending(null);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidDescendingConstruction() {
        Sort.descending(null);
    }

    @Test
    public void testToString() {
        Sort<String> sort = Sort.ascending("attr");
        Assert.assertFalse(sort.toString().isEmpty());
    }

    @Test
    public void testEqualsAndHashCode() {
        Sort<String> obj = Sort.ascending("attr");
        Sort<String> equal1 = Sort.ascending("attr");
        Sort<String> equal2 = Sort.ascending("attr");
        Sort<String> unequal1 = Sort.descending("attr");
        Sort<String> unequal2 = Sort.ascending("other attr");
        EqualityTester.testEqualsAndHashCode(obj, equal1, equal2, unequal1, unequal2);
    }
}
