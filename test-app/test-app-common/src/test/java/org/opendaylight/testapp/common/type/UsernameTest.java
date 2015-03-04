/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.type;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class UsernameTest {

    @Test
    public void testValueOf() {
        Username username = Username.valueOf("user");
        Assert.assertEquals("user", username.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        // null value is already tested by SerializableValueTypeTest
        final String invalidValue = "";
        Username.valueOf(invalidValue);
    }
}
