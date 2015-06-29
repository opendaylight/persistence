//------------------------------------------------------------------------------
// Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Public License v1.0 which accompanies this distribution,
// and is available at http://www.eclipse.org/legal/epl-v10.html
//------------------------------------------------------------------------------
package org.opendaylight.testapp.cli;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;

import java.util.List;

/**
 * The NetworkDevice getReachable command.
 */
@Command(name = "get-reachable", scope = "networkdevice", description = "NetworkDeviceService")
public class NetworkDeviceGetReachableCommand extends OsgiCommandSupport {
    private final NetworkDeviceService networkDeviceService;

    /**
     * Instantiates a new Network device get reachable command.
     *
     * @param networkDeviceService the network device service
     */
    public NetworkDeviceGetReachableCommand(NetworkDeviceService networkDeviceService) {
        this.networkDeviceService = networkDeviceService;
    }

    @Override
    protected Object doExecute() throws Exception {
        List<NetworkDevice> devices = networkDeviceService.getReachable();
        StringBuilder stringBuilder = new StringBuilder();
        for (NetworkDevice device : devices) {
            stringBuilder.append(device.toString());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
