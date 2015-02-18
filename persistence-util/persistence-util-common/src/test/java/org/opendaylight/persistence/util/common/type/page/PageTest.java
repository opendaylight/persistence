/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class PageTest {

    @Test
    public void testConstruction() {
        Page<PageRequest, String> page = new Page<PageRequest, String>(new PageRequest(10), new ArrayList<String>());
        Assert.assertEquals(new PageRequest(10), page.getRequest());
    }

    @SuppressWarnings("unused")
    @Test (expected = NullPointerException.class)
    public void testInvalidPageRequest() {
        final PageRequest invalidPageRequest = null;
        final List<String> validData = new ArrayList<String>();
        new Page<PageRequest, String>(invalidPageRequest, validData);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testValidPageRequestInvalidData() {
        final PageRequest validPageRequest = new PageRequest(10);
        final List<String> invalidData = null;

        new Page<PageRequest, String>(validPageRequest, invalidData);
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<Page<?, ?>> semanticVerifier = new SemanticCompatibilityVerifier<Page<?, ?>>() {
            @Override
            public void assertSemanticCompatibility(Page<?, ?> original, Page<?, ?> replica) {
                Assert.assertEquals(original.getRequest(), replica.getRequest());
                Assert.assertEquals(original.getData(), replica.getData());
            }
        };

        Page<PageRequest, String> page = new Page<PageRequest, String>(new PageRequest(10), "Serializable Data");
        SerializabilityTester.testSerialization(page, semanticVerifier);
    }
}

