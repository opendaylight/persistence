/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.jpql;

import javax.persistence.Query;

import com.google.common.base.Joiner;

/**
 * Binary associative and commutative operator.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
abstract class JpqlBinaryOperator implements JpqlPredicate {

    private String operator;
    private JpqlPredicate[] operands;

    /**
     * Creates a binary, associative and commutative operator.
     * 
     * @param operator
     *            operator
     * @param operands
     *            operands
     */
    protected JpqlBinaryOperator(String operator, JpqlPredicate... operands) {
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public String getPredicate() {
        if (this.operands == null || this.operands.length <= 0) {
            return "";
        }
        Joiner joiner = Joiner.on("");
        for (JpqlPredicate operand : this.operands) {
            joiner.join("(", operand.getPredicate(), ")", this.operator);
        }

        return Joiner.on("").join("(", joiner.toString(), ")");
    }

    @Override
    public void addParameters(Query query) {
        if (this.operands != null) {
            for (JpqlPredicate operand : this.operands) {
                operand.addParameters(query);
            }
        }
    }
}
