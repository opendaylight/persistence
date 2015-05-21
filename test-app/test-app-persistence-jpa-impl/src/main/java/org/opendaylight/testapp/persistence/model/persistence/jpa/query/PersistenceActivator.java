/**
 * 
 */
package org.opendaylight.testapp.persistence.model.persistence.jpa.query;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class PersistenceActivator implements BundleActivator {

	ServiceRegistration serviceRegistration;
	
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
