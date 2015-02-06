/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test;

import org.junit.Assert;

/**
 * Assert utility methods.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class AssertUtil {

    private AssertUtil() {

    }

    /*
     * All methods in this class could return boolean instead of executing asserts internally,
     * however returning a boolean would make detecting the failure's root cause more complicated.
     * Normally the methods provided by this class assert complex conditions which usually involve
     * multiple comparisons (internal asserts). When the test that is using this class fails, the
     * error is more precise: the line number will tell which condition failed for example along
     * with the expected and actual values.
     */

    /**
     * Asserts that {@code str} contains {@code infix}.
     * 
     * @param infix expected content
     * @param str string to assert
     */
    public static void assertContains(String infix, String str) {
        Assert.assertNotNull(infix);
        Assert.assertNotNull(str);
        Assert.assertTrue("Expected <" + infix + "> contained in <" + str + ">", str.contains(infix));
    }

    /**
     * Asserts that {@code str} starts with {@code prefix}.
     * 
     * @param prefix expected prefix
     * @param str string to assert
     */
    public static void assertStartsWith(String prefix, String str) {
        Assert.assertNotNull(prefix);
        Assert.assertNotNull(str);
        Assert.assertTrue("Expected <" + prefix + "> as prefix of <" + str + ">", str.startsWith(prefix));
    }

    /**
     * Asserts that {@code str} ends with {@code suffix}.
     * 
     * @param suffix expected prefix
     * @param str string to assert
     */
    public static void assertEndsWith(String suffix, String str) {
        Assert.assertNotNull(suffix);
        Assert.assertNotNull(str);
        Assert.assertTrue("Expected <" + suffix + "> as suffix of <" + str + ">", str.endsWith(suffix));
    }
}
