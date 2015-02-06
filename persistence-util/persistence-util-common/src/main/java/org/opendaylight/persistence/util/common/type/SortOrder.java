/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

/**
 * Specifies the order in which to sort a list of items.
 *
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public enum SortOrder {
    /**
     * Sort from smallest to largest. For example, from A to Z.
     */
    ASCENDING,
    /**
     * Sort from largest to smallest. For example, from Z to A.
     */
    DESCENDING
}