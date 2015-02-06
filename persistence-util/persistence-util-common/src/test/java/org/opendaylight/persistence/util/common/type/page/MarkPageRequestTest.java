/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type.page;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.common.type.page.MarkPageRequest.Navigation;
import org.opendaylight.persistence.util.test.EqualityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;
import org.opendaylight.persistence.util.test.ThrowableTester;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class MarkPageRequestTest {

    @Test
    public void testConstruction() {
        MarkPageRequest<String> pageRequest = new MarkPageRequest<String>(10);
        Assert.assertNull(pageRequest.getMark());
        Assert.assertEquals(MarkPageRequest.Navigation.NEXT, pageRequest.getNavigation());
        Assert.assertEquals(10, pageRequest.getSize());

        pageRequest = new MarkPageRequest<String>("Mark", Navigation.PREVIOUS, 10);
        Assert.assertEquals("Mark", pageRequest.getMark());
        Assert.assertEquals(Navigation.PREVIOUS, pageRequest.getNavigation());
        Assert.assertEquals(10, pageRequest.getSize());
    }

    @Test
    @SuppressWarnings("unused")
    public void testInvalidConstruction() {
        final int validSize = 1;
        final int invalidSize = 0;
        final String validMark = "Mark";
        final Navigation invalidNavigation = null;

        ThrowableTester.testThrows(IllegalArgumentException.class, new ThrowableTester.Instruction() {
            @Override
            public void execute() throws Throwable {
                new MarkPageRequest<String>(invalidSize);
            }
        });

        ThrowableTester.testThrows(NullPointerException.class, new ThrowableTester.Instruction() {
            @Override
            public void execute() throws Throwable {
                new MarkPageRequest<String>(validMark, invalidNavigation, validSize);
            }
        });
    }

    @Test
    public void testEqualsAndHashCode() {
        MarkPageRequest<String> objA0 = new MarkPageRequest<String>("mark", MarkPageRequest.Navigation.NEXT, 10);
        MarkPageRequest<String> objA1 = new MarkPageRequest<String>("mark", Navigation.NEXT, 10);
        MarkPageRequest<String> objA2 = new MarkPageRequest<String>("mark", Navigation.NEXT, 10);
        MarkPageRequest<String> objB = new MarkPageRequest<String>("mark 2", Navigation.NEXT, 10);
        MarkPageRequest<String> objC = new MarkPageRequest<String>("mark", Navigation.PREVIOUS, 10);
        MarkPageRequest<String> objD = new MarkPageRequest<String>("mark", Navigation.NEXT, 1);

        EqualityTester.testEqualsAndHashCode(objA0, objA1, objA2, objB, objC, objD);
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<MarkPageRequest<?>> semanticVerifier = new SemanticCompatibilityVerifier<MarkPageRequest<?>>() {
            @Override
            public void assertSemanticCompatibility(MarkPageRequest<?> original, MarkPageRequest<?> replica) {
                Assert.assertEquals(original.getSize(), replica.getSize());
                Assert.assertEquals(original.getMark(), replica.getMark());
                Assert.assertEquals(original.getNavigation(), replica.getNavigation());
            }
        };

        SerializabilityTester.testSerialization(new MarkPageRequest<String>("mark", Navigation.NEXT, 10),
                semanticVerifier);
    }
}


