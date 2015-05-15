package org.opendaylight.persistence.util.common;

public interface Stoppable {

    /**
     * Stops (shutdowns) operations. Resources will be released.
     * 
     * @throws Exception if errors occur while stopping
     */
    public void stop() throws Exception;
}
