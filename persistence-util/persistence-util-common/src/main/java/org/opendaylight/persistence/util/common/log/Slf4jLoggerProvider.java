package org.opendaylight.persistence.util.common.log;

public class Slf4jLoggerProvider implements LoggerProvider<Class<?>> {

    private boolean includeMethodTrace;
    private Logger defaultLogger;

    /**
     * Creates a logger provider.
     */
    public Slf4jLoggerProvider() {
        this(false);
    }

    /**
     * Creates a logger provider.
     * 
     * @param includeMethodTrace {@code true} to include in the log message the method that is
     *            creating the log. Default value is {@code false}. Since {@link Logger} is actually
     *            delegating to {@link org.slf4j.Logger}, the method trace might not be printed
     *            correctly by {@link org.slf4j.Logger}. Thus, if {@code includeMethodTrace} is
     *            {@code true}, the method trace is added to the log message.
     */
    public Slf4jLoggerProvider(boolean includeMethodTrace) {
        this.includeMethodTrace = includeMethodTrace;
        this.defaultLogger = new LoggerImpl(null, this.includeMethodTrace);
    }

    @Override
    public Logger getLogger() {
        return this.defaultLogger;
    }

    @Override
    public Logger getLogger(Class<?> component) {
        return new LoggerImpl(component, this.includeMethodTrace);
    }

    private static class LoggerImpl implements Logger {

        private org.slf4j.Logger delegate;
        private boolean includeMethodTrace;

        public LoggerImpl(Class<?> component, boolean includeMethodTrace) {
            this.includeMethodTrace = includeMethodTrace;
            if (component != null) {
                this.delegate = org.slf4j.LoggerFactory.getLogger(component);
            }
            else {
                this.delegate = org.slf4j.LoggerFactory.getLogger("Default");
            }
        }

        @Override
        public void error(String message) {
            this.delegate.error(prepareMessage(message));
        }

        @Override
        public void error(String message, Throwable cause) {
            this.delegate.error(prepareMessage(message), cause);
        }

        @Override
        public void warning(String message) {
            this.delegate.warn(prepareMessage(message));
        }

        @Override
        public void warning(String message, Throwable cause) {
            this.delegate.warn(prepareMessage(message), cause);
        }

        @Override
        public void info(String message) {
            this.delegate.info(prepareMessage(message));
        }

        @Override
        public void info(String message, Throwable cause) {
            this.delegate.info(prepareMessage(message), cause);
        }

        @Override
        public void debug(String message) {
            this.delegate.debug(prepareMessage(message));
        }

        @Override
        public void debug(String message, Throwable cause) {
            this.delegate.debug(prepareMessage(message), cause);
        }

        private String prepareMessage(String message) {
            if (this.includeMethodTrace) {
                return Thread.currentThread().getStackTrace()[3].toString() + " " + message;
            }
            return message;
        }
    }
}
