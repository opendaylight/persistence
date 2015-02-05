/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.EqualityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class IdTest {

    public void testConstruction() {
        Id<String, String> id = Id.valueOf("id");
        Assert.assertEquals("id", id.getValue());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidConstruction() {
        Id<String, String> invalidId = null;
        Id.valueOf(invalidId);
    }

    @Test
    public void testEqualsAndHashCode() {
        Id<String, String> obj = Id.valueOf("id");
        Id<String, String> equal1 = Id.valueOf("id");
        Id<String, String> equal2 = Id.valueOf("id");
        Id<String, String> unequal = Id.valueOf("other id");

        EqualityTester.testEqualsAndHashCode(obj, equal1, equal2, unequal);
    }

    @Test
    public void testSerialization() {
        SerializabilityTester.SemanticCompatibilityVerifier<Id<?, ?>> semanticVerifier = new SerializabilityTester.SemanticCompatibilityVerifier<Id<?, ?>>() {
            @Override
            public void assertSemanticCompatibility(Id<?, ?> original, Id<?, ?> replica) {
                Assert.assertEquals(original.getValue(), replica.getValue());
            }
        };

        SerializabilityTester.testSerialization(Id.valueOf("id"), semanticVerifier);
    }
}
