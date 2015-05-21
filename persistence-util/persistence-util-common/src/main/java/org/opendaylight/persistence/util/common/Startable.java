package org.opendaylight.persistence.util.common;

/**
 * Interface to start operations.
 * 
 */
public interface Startable {

    /**
     * Starts operations.
     * 
     * @throws Exception if errors occur while starting
     */
    public void start() throws Exception;
}
