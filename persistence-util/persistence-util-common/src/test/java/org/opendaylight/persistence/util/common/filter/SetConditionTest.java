/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.common.Converter;
import org.opendaylight.persistence.util.common.filter.SetCondition.Mode;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;
import org.opendaylight.persistence.util.test.ThrowableTester;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class SetConditionTest {

    @Test
    public void testInWithSet() {
        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        SetCondition<Integer> condition = SetCondition.in(values);
        Assert.assertEquals(values, condition.getValues());
        Assert.assertEquals(Mode.IN, condition.getMode());
    }

    @Test
    public void testInWithVarargs() {
        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        SetCondition<Integer> condition = SetCondition.in(Integer.valueOf(1), Integer.valueOf(2));
        Assert.assertEquals(values, condition.getValues());
        Assert.assertEquals(Mode.IN, condition.getMode());
    }

    @Test
    public void testNotInWithSet() {
        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        SetCondition<Integer> condition = SetCondition.notIn(values);
        Assert.assertEquals(values, condition.getValues());
        Assert.assertEquals(Mode.NOT_IN, condition.getMode());
    }

    @Test
    public void testNotInWithVarargs() {
        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        SetCondition<Integer> condition = SetCondition.notIn(Integer.valueOf(1), Integer.valueOf(2));
        Assert.assertEquals(values, condition.getValues());
        Assert.assertEquals(Mode.NOT_IN, condition.getMode());
    }

    @Test
    public void testConvert() {
        Converter<Integer, String> converter = new Converter<Integer, String>() {
            @Override
            public String convert(Integer source) {
                return source.toString();
            }
        };

        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        Set<String> convertedValues = new HashSet<String>(Arrays.asList("1", "2"));
        final SetCondition<Integer> condition = SetCondition.in(values);
        SetCondition<String> convertedCondition = condition.convert(converter);
        Assert.assertEquals(convertedValues, convertedCondition.getValues());
        Assert.assertEquals(condition.getMode(), convertedCondition.getMode());
    }

    @Test
    public void testConvertInvalid() {
        final Converter<Integer, String> invalidConverter = null;
        Set<Integer> values = new HashSet<Integer>(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2)));
        final SetCondition<Integer> condition = SetCondition.in(values);

        ThrowableTester.testThrows(NullPointerException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                condition.convert(invalidConverter);
            }
        });
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<SetCondition<Integer>> semanticVerifier = new SemanticCompatibilityVerifier<SetCondition<Integer>>() {
            @Override
            public void assertSemanticCompatibility(SetCondition<Integer> original, SetCondition<Integer> replica) {
                Assert.assertEquals(original.getMode(), replica.getMode());
                Assert.assertEquals(original.getValues(), replica.getValues());
            }
        };

        SerializabilityTester.testSerialization(SetCondition.in(Integer.valueOf(1)), semanticVerifier);
    }

    @Test
    public void testToString() {
        SetCondition<Integer> condition = SetCondition.in(Integer.valueOf(1));
        Assert.assertFalse(condition.toString().isEmpty());
    }
}
