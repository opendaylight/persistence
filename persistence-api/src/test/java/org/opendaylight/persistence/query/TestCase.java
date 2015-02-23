/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import java.io.Serializable;

import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
class TestCase {

    public static class Key implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    public static class MyIdentifiable implements Identifiable<Key> {

        @Override
        public Key getIdentifier() {
            return null;
        }
    }

    public static class Filter {

    }

    public static class SortKey {

    }

    public static class Context {

    }
}
