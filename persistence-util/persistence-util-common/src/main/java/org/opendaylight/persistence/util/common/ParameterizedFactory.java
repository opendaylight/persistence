/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common;

/**
 * Factory.
 * 
 * @param <T>
 *            type of the object to create
 * @param <I>
 *            type of the factory input
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface ParameterizedFactory<T, I> {

    /**
     * Creates an object.
     * 
     * @param input
     *            input
     * @return a new object
     */
    T create(I input);
}
