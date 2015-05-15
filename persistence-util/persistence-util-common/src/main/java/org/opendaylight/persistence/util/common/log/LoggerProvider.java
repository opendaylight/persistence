package org.opendaylight.persistence.util.common.log;

/**
 * Logger provider.
 * 
 * @param <C> Type of the component to provide loggers for
 * @author Fabiel Zuniga
 */
public interface LoggerProvider<C> {

    /**
     * Gets the default logger.
     *
     * @return default logger
     */
    public Logger getLogger();

    /**
     * Gets a logger for the given module.
     *
     * @param component component to get the logger for
     * @return A logger for the given component
     */
    public Logger getLogger(C component);
}

