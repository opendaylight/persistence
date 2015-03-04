/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.util.common.filter.TimePeriodCondition.Mode;
import org.opendaylight.persistence.util.common.type.TimePeriod;
import org.opendaylight.persistence.util.test.SerializabilityTester;
import org.opendaylight.persistence.util.test.SerializabilityTester.SemanticCompatibilityVerifier;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class TimePeriodConditionTest {

    @Test
    public void testIn() {
        TimePeriod value = TimePeriod.getDayPeriod(new Date(), TimeZone.getDefault());
        TimePeriodCondition condition = TimePeriodCondition.in(value);
        Assert.assertEquals(value, condition.getValue());
        Assert.assertEquals(Mode.IN, condition.getMode());
    }

    @Test
    public void testNotIn() {
        TimePeriod value = TimePeriod.getDayPeriod(new Date(), TimeZone.getDefault());
        TimePeriodCondition condition = TimePeriodCondition.notIn(value);
        Assert.assertEquals(value, condition.getValue());
        Assert.assertEquals(Mode.NOT_IN, condition.getMode());
    }

    @Test
    public void testSerialization() {
        SemanticCompatibilityVerifier<TimePeriodCondition> semanticVerifier = new SemanticCompatibilityVerifier<TimePeriodCondition>() {
            @Override
            public void assertSemanticCompatibility(TimePeriodCondition original, TimePeriodCondition replica) {
                Assert.assertEquals(original.getMode(), replica.getMode());
                Assert.assertEquals(original.getValue(), replica.getValue());
            }
        };

        TimePeriod value = TimePeriod.getDayPeriod(new Date(), TimeZone.getDefault());
        SerializabilityTester.testSerialization(TimePeriodCondition.in(value), semanticVerifier);
    }

    @Test
    public void testToString() {
        TimePeriod value = TimePeriod.getDayPeriod(new Date(), TimeZone.getDefault());
        TimePeriodCondition condition = TimePeriodCondition.in(value);
        Assert.assertFalse(condition.toString().isEmpty());
    }
}
