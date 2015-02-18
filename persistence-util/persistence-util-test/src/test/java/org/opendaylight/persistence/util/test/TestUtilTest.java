/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class TestUtilTest {

    @Test
    public void testGetSetPrivateField() throws Exception {
        Object object = new Object() {
            @SuppressWarnings("unused")
            private String field = "Hello World";
        };

        final String fieldName = "field";

        Assert.assertEquals("Hello World", TestUtil.getPrivateField(fieldName, object));

        final String newValue = "Goodbye world";
        TestUtil.setPrivateField(fieldName, newValue, object);
        Assert.assertEquals(newValue, TestUtil.getPrivateField(fieldName, object));
    }

    @Test
    public void testSetPrivateFieldInvalidCall() throws Exception {
        final String validFieldName = "field";
        final String invalidFieldNameNull = null;
        final String invalidFieldNameEmpty = "";

        final Object validValue = null;

        final Object validObject = EasyMock.createMock(Object.class);
        final Object invalidObject = null;

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(invalidFieldNameNull, validValue, validObject);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(invalidFieldNameEmpty, validValue, validObject);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(validFieldName, validValue, invalidObject);
            }
        });
    }

    @Test
    public void testSetPrivateFieldWithDeclaredFieldClassInvalidCall() throws Exception {
        final String validFieldName = "field";
        final String invalidFieldNameNull = null;
        final String invalidFieldNameEmpty = "";

        final Object validValue = null;

        final Object validObject = EasyMock.createMock(Object.class);
        final Object invalidObject = null;

        final Class<Object> validDeclaredFieldClass = Object.class;
        final Class<Object> invalidDeclaredFieldClass = null;

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(invalidFieldNameNull, validValue, validObject, validDeclaredFieldClass);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(invalidFieldNameEmpty, validValue, validObject, validDeclaredFieldClass);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(validFieldName, validValue, invalidObject, validDeclaredFieldClass);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.setPrivateField(validFieldName, validValue, validObject, invalidDeclaredFieldClass);
            }

        });
    }

    @Test
    public void testGetPrivateFieldInvalidCall() throws Exception {
        final String validFieldName = "field";
        final String invalidFieldNameNull = null;
        final String invalidFieldNameEmpty = "";

        final Object validObject = EasyMock.createMock(Object.class);
        final Object invalidObject = null;

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(invalidFieldNameNull, validObject);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(invalidFieldNameEmpty, validObject);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(validFieldName, invalidObject);
            }

        });
    }

    @Test
    public void testGetPrivateFieldWithDeclaredFieldClassInvalidCall() throws Exception {
        final String validFieldName = "field";
        final String invalidFieldNameNull = null;
        final String invalidFieldNameEmpty = "";

        final Object validObject = EasyMock.createMock(Object.class);
        final Object invalidObject = null;

        final Class<Object> validDeclaredFieldClass = Object.class;
        final Class<Object> invalidDeclaredFieldClass = null;

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(invalidFieldNameNull, validObject, validDeclaredFieldClass);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(invalidFieldNameEmpty, validObject, validDeclaredFieldClass);
            }

        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(validFieldName, invalidObject, validDeclaredFieldClass);
            }
        });

        ThrowableTester.testThrows(IllegalArgumentException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                TestUtil.getPrivateField(validFieldName, validObject, invalidDeclaredFieldClass);
            }
        });
    }

    @Test
    public void testGetExecutingMethod() {
        StackTraceElement executingMethod = TestUtil.getExecutingMethod();
        Assert.assertEquals(getClass().getName(), executingMethod.getClassName());
        Assert.assertEquals("testGetExecutingMethod", executingMethod.getMethodName());
    }
}
