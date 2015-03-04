/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

import java.util.Date;

import org.opendaylight.yangtools.concepts.Identifiable;

import com.google.common.base.MoreObjects;

/**
 * TSDR Metric.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class TsdrMetric implements Identifiable<Long> {

    private Long id;

    private final String name;
    private final String value;
    private final Date timestamp;

    /**
     * Creates a metric.
     * 
     * @param name metric's name
     * @param value metric's value
     * @param timestamp timestamp the metric was recorded at
     */
    public TsdrMetric(String name, String value, Date timestamp) {
        this(null, name, value, timestamp);
    }

    /**
     * Creates a metric.
     * 
     * @param id metric's id
     * @param name metric's name
     * @param value metric's value
     * @param timestamp timestamp the metric was recorded at
     */
    public TsdrMetric(Long id, String name, String value, Date timestamp) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    @Override
    public Long getIdentifier() {
        return this.id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).add("name", this.name).add("value", this.value)
                .add("timestamp", this.timestamp).toString();
    }
}
