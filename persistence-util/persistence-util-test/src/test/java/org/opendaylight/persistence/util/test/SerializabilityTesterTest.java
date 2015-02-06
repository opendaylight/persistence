/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;
import org.opendaylight.persistence.util.test.ThrowableTester.Validator;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class SerializabilityTesterTest {

    @Test
    public void testTestSerialization() {
        PortableClass serializable = new PortableClass();
        serializable.setAttrPreviousVersion("previous attr");
        serializable.setAttrCurrentVersion("current attr");

        // Test binary and semantic compatibility

        SemanticCompatibilityVerifier<PortableClass> semanticVerifier = new SemanticCompatibilityVerifier<PortableClass>() {
            @Override
            public void assertSemanticCompatibility(PortableClass original, PortableClass replica) {
                // System.out.println(original);
                // System.out.println(replica);

                Assert.assertEquals(original.getAttrPreviousVersion(), replica.getAttrPreviousVersion());
                Assert.assertEquals(original.getAttrCurrentVersion(), replica.getAttrCurrentVersion());
            }
        };

        SerializabilityTester.testSerialization(serializable, semanticVerifier);
    }

    @Test
    public void testTestSerializationFailure() {
        final InvalidSerializable serializable = new InvalidSerializable();

        Validator<AssertionError> errorValidator = new Validator<AssertionError>() {
            @Override
            public void assertThrowable(AssertionError error) {
                String expectedError = "Serialization failure:";
                AssertUtil.assertStartsWith(expectedError, error.getMessage());

            }
        };

        ThrowableTester.testThrowsAny(AssertionError.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                SerializabilityTester.testSerialization(serializable, null);
            }
        }, errorValidator);
    }

    private static class InvalidSerializable implements Serializable {
        private static final long serialVersionUID = 1L;
        @SuppressWarnings("unused")
        private Object object = new Object();
    }
}
