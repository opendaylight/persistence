/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.tuple;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.EqualityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class PairTest {

    @Test
    public void testEqualsAndHashCode() {
        Pair<String, Integer> obj = Pair.valueOf("Hello World", Integer.valueOf(1));
        Pair<String, Integer> equal1 = Pair.valueOf("Hello World", Integer.valueOf(1));
        Pair<String, Integer> equal2 = Pair.valueOf("Hello World", Integer.valueOf(1));
        Pair<String, Integer> unequal1 = Pair.valueOf("Different value", Integer.valueOf(1));
        Pair<String, Integer> unequal2 = Pair.valueOf("Hello World", Integer.valueOf(2));
        Pair<String, Integer> unequal3 = Pair.valueOf("Different value", Integer.valueOf(2));

        EqualityTester.testEqualsAndHashCode(obj, equal1, equal2, unequal1, unequal2, unequal3);

        obj = Pair.valueOf(null, null);
        equal1 = Pair.valueOf(null, null);
        equal2 = Pair.valueOf(null, null);
        unequal1 = Pair.valueOf("Different value", Integer.valueOf(1));

        EqualityTester.testEqualsAndHashCode(obj, equal1, equal2, unequal1);
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<Pair<?, ?>> semanticVerifier = new SemanticCompatibilityVerifier<Pair<?, ?>>() {
            @Override
            public void assertSemanticCompatibility(Pair<?, ?> original, Pair<?, ?> replica) {
                Assert.assertEquals(original.getFirst(), replica.getFirst());
                Assert.assertEquals(original.getSecond(), replica.getSecond());
            }
        };

        SerializabilityTester.testSerialization(Pair.valueOf(Integer.valueOf(0), "Hello World"), semanticVerifier);
    }

    @Test
    public void testToString() {
        Assert.assertNotNull(Pair.valueOf("Hello World", Integer.valueOf(0).toString()));
    }
}
