/**
 * 
 */
package org.opendaylight.testapp.persistence.model.persistence.jpa.query;

import org.opendaylight.persistence.util.common.log.LoggerProvider;
import org.opendaylight.persistence.util.common.log.Slf4jLoggerProvider;
import org.opendaylight.testapp.persistence.PersistenceService;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;
import org.opendaylight.testapp.persistence.bl.impl.NetworkDeviceServiceImpl;
import org.opendaylight.testapp.persistence.model.persistence.jpa.query.JpaPersistenceService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class PersistenceActivator implements BundleActivator {

	ServiceRegistration serviceRegistration;
	private static final LoggerProvider<Class<?>> loggerProvider = new Slf4jLoggerProvider();
	
	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.print("PersistenceActivator STarted");

		// PersistenceService persistenceService = new JpaPersistenceService(loggerProvider);
		//serviceRegistration = context.registerService(PersistenceService.class.getName(), persistenceService, null);
        
        // NetworkDeviceService networkDeviceService = new NetworkDeviceServiceImpl(persistenceService, loggerProvider);
        // serviceRegistration = context.registerService(NetworkDeviceService.class.getName(), networkDeviceService, null);
	}

	/* (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		serviceRegistration.unregister();

	}

}
