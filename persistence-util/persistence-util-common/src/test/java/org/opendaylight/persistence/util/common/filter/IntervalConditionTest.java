/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.common.Converter;
import org.opendaylight.persistence.util.common.filter.IntervalCondition.Mode;
import org.opendaylight.persistence.util.common.type.Interval;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;
import org.opendaylight.persistence.util.test.ThrowableTester;
import org.opendaylight.persistence.util.test.ThrowableTester.Instruction;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class IntervalConditionTest {

    @Test
    public void testIn() {
        Interval<Integer> value = Interval.unbounded();
        IntervalCondition<Integer> condition = IntervalCondition.in(value);
        Assert.assertEquals(value, condition.getValue());
        Assert.assertEquals(Mode.IN, condition.getMode());
    }

    @Test
    public void testNotIn() {
        Interval<Integer> value = Interval.unbounded();
        IntervalCondition<Integer> condition = IntervalCondition.notIn(value);
        Assert.assertEquals(value, condition.getValue());
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

        Integer leftEndPoint = Integer.valueOf(1);
        Integer rightEndPoint = Integer.valueOf(2);
        final IntervalCondition<Integer> condition = IntervalCondition.in(Interval.open(leftEndPoint, rightEndPoint));
        IntervalCondition<String> convertedCondition = condition.convert(converter);
        Assert.assertEquals(converter.convert(leftEndPoint), convertedCondition.getValue().getLeftEndpoint());
        Assert.assertEquals(converter.convert(rightEndPoint), convertedCondition.getValue().getRightEndpoint());
        Assert.assertEquals(condition.getValue().getType(), convertedCondition.getValue().getType());
        Assert.assertEquals(condition.getMode(), convertedCondition.getMode());
    }

    @Test
    public void testConvertInvalid() {
        final Converter<Integer, String> invalidConverter = null;
        Integer leftEndPoint = Integer.valueOf(1);
        Integer rightEndPoint = Integer.valueOf(2);
        final IntervalCondition<Integer> condition = IntervalCondition.in(Interval.open(leftEndPoint, rightEndPoint));

        ThrowableTester.testThrows(NullPointerException.class, new Instruction() {
            @Override
            public void execute() throws Throwable {
                condition.convert(invalidConverter);
            }
        });
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<IntervalCondition<Integer>> semanticVerifier = new SemanticCompatibilityVerifier<IntervalCondition<Integer>>() {
            @Override
            public void assertSemanticCompatibility(IntervalCondition<Integer> original,
                    IntervalCondition<Integer> replica) {
                Assert.assertEquals(original.getMode(), replica.getMode());
                Assert.assertEquals(original.getValue(), replica.getValue());
            }
        };

        Interval<Integer> value = Interval.unbounded();
        SerializabilityTester.testSerialization(IntervalCondition.in(value), semanticVerifier);
    }

    @Test
    public void testToString() {
        Interval<Integer> value = Interval.unbounded();
        IntervalCondition<Integer> condition = IntervalCondition.in(value);
        Assert.assertFalse(condition.toString().isEmpty());
    }
}
