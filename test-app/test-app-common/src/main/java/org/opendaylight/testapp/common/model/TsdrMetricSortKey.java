/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

/**
 * TSDR metric sort key
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public enum TsdrMetricSortKey {

    /**
     * Timestamp is used for sorting
     */
    TIMESTAMP,
    /**
     * The metric's value is used for sorting
     */
    VALUE
}
