/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test.easymock;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.test.AssertUtil;
import org.opendaylight.persistence.util.test.ThrowableTester;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;
import org.opendaylight.persistence.util.test.ThrowableTester.Validator;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class ArgumentMatcherTest {

    @Test
    public void testNullMatch() {
        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>("MyArgumentMatcher") {
            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return true;
            }
        };

        Assert.assertFalse(matcher.matches(null));
    }

    @Test
    public void testInvalidTypeMatch() {
        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>("MyArgumentMatcher") {
            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return true;
            }
        };

        Assert.assertFalse(matcher.matches(Integer.valueOf(1)));
    }

    @Test
    public void testVerifyMatchablesPositive() {
        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>("MyArgumentMatcher") {
            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return true;
            }
        };

        Matchable<Integer> matchable1 = Matchable.valueOf("positive", Integer.valueOf(1), Integer.valueOf(1));
        Matchable<String> matchable2 = Matchable.valueOf("positive", "some value", "some value");
        Assert.assertTrue(matcher.verify(matchable1, matchable2));
    }

    @Test
    public void testVerifyMatchablesNegative() {
        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>("MyArgumentMatcher") {
            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return true;
            }
        };

        Matchable<Integer> matchable1 = Matchable.valueOf("positive", Integer.valueOf(1), Integer.valueOf(1));
        Matchable<String> matchable2 = Matchable.valueOf("negative", "some value", "some other value");
        Assert.assertFalse(matcher.verify(matchable1, matchable2));
    }

    @Test
    public void testMatch() {
        final String argumentName = "MyArgumentMatcher";
        final String argumentPropertyName = "property";
        final String argumentPropertyExpectedValue = "expected-value";
        final String argumentPropertyActualValue = "expected-value";

        Mockable mockable = EasyMock.createMock(Mockable.class);

        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>(argumentName) {

            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return verify(Matchable.<String> valueOf(argumentPropertyName, argumentPropertyExpectedValue,
                        argument.getValue()));
            }
        };

        mockable.myMethod(matcher.match());
        EasyMock.replay(mockable);
        mockable.myMethod(new MyArgument<String>(argumentPropertyActualValue));
        EasyMock.verify(mockable);
    }

    @Test
    public void testMatchFail() {
        final String argumentName = "MyArgumentMatcher";
        final String argumentPropertyName = "property";
        final String argumentPropertyExpectedValue = "expected-value";
        final String argumentPropertyActualValue = "different-value";

        final Mockable mockable = EasyMock.createMock(Mockable.class);

        ArgumentMatcher<MyArgument<String>> matcher = new ArgumentMatcher<MyArgument<String>>(argumentName) {

            @Override
            public boolean verifyMatch(MyArgument<String> argument) {
                return verify(Matchable.<String> valueOf(argumentPropertyName, argumentPropertyExpectedValue,
                        argument.getValue()));
            }
        };

        mockable.myMethod(matcher.match());
        EasyMock.replay(mockable);

        ThrowableTester.testThrows(AssertionError.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                mockable.myMethod(new MyArgument<String>(argumentPropertyActualValue));
                EasyMock.verify(mockable);
            }
        }, new Validator<AssertionError>() {
            @Override
            public void assertThrowable(AssertionError throwable) {
                AssertUtil.assertContains(argumentName, throwable.getMessage());
                AssertUtil.assertContains(argumentPropertyName, throwable.getMessage());
                AssertUtil.assertContains(argumentPropertyExpectedValue, throwable.getMessage());
                AssertUtil.assertContains(argumentPropertyActualValue, throwable.getMessage());
            }
        });
    }

    @Test
    public void testIArgumentMatcherMatch() {
        Mockable mockable = EasyMock.createMock(Mockable.class);

        IArgumentMatcher matcher = new IArgumentMatcher() {

            @Override
            public void appendTo(StringBuffer buffer) {
                buffer.append("<MyArgumentMatcher>");
            }

            @Override
            public boolean matches(Object arg) {
                if (arg == null) {
                    return false;
                }

                if (!(arg instanceof MyArgument<?>)) {
                    return false;
                }

                @SuppressWarnings("unchecked")
                MyArgument<String> argument = (MyArgument<String>) arg;

                if (argument.getValue() == null) {
                    return false;
                }

                if (!argument.getValue().equals("Hello World")) {
                    return false;
                }

                return true;
            }
        };

        mockable.myMethod(ArgumentMatcher.<MyArgument<String>> match(matcher));
        EasyMock.replay(mockable);
        mockable.myMethod(new MyArgument<String>("Hello World"));
        EasyMock.verify(mockable);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIArgumentMatcherMatchInvalid() {
        IArgumentMatcher invalidMatcher = null;
        ArgumentMatcher.<MyArgument<String>> match(invalidMatcher);
    }

    private static interface Mockable {
        public void myMethod(MyArgument<String> argument);
    }

    private static class MyArgument<T> {
        private T value;

        public MyArgument(T value) {
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }
    }
}
