/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;


import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.EqualityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class PropertyTest {

    @Test
    public void testConstruction() {
        Object identity = EasyMock.createMock(Object.class);
        Object value = EasyMock.createMock(Object.class);

        Property<Object, Object> property = Property.valueOf(identity, value);

        Assert.assertSame(identity, property.getIdentity());
        Assert.assertSame(value, property.getValue());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidConstruction() {
        Object invalidIdentity = null;
        Object validValue = null;
        Property.valueOf(invalidIdentity, validValue);
    }

    @Test
    public void testEqualsAndHashCode() {
        Object identity = new Object();

        Property<Object, Object> obj = Property.valueOf(identity, new Object());
        Property<Object, Object> equal1 = Property.valueOf(identity, new Object());
        Property<Object, Object> equal2 = Property.valueOf(identity, new Object());
        Property<Object, Object> unequal = Property.valueOf(new Object(), new Object());

        EqualityTester.testEqualsAndHashCode(obj, equal1, equal2, unequal);
    }

    @Test
    public void testSerialization() {
        SerializabilityTester.SemanticCompatibilityVerifier<Property<?, ?>> semanticVerifier = new SerializabilityTester.SemanticCompatibilityVerifier<Property<?, ?>>() {
            @Override
            public void assertSemanticCompatibility(Property<?, ?> original, Property<?, ?> replica) {
                Assert.assertEquals(original.getIdentity(), replica.getIdentity());
                Assert.assertEquals(original.getValue(), replica.getValue());
            }
        };

        SerializabilityTester.testSerialization(Property.valueOf("subject", "subject"), semanticVerifier);
    }

    @Test
    public void testToString() {
        Property<String, String> property = Property.valueOf("subject", "subject");
        Assert.assertFalse(property.toString().isEmpty());
    }
}
