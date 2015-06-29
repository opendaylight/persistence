//------------------------------------------------------------------------------
// Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Public License v1.0 which accompanies this distribution,
// and is available at http://www.eclipse.org/legal/epl-v10.html
//------------------------------------------------------------------------------
package org.opendaylight.testapp.cli;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;

/**
 * The NetworkDevice discover command.
 */
@Command(name = "discover", scope = "networkdevice", description = "NetworkDeviceService")
public class NetworkDeviceDiscoverCommand extends OsgiCommandSupport {
    private final NetworkDeviceService networkDeviceService;

    /**
     * The Ip address.
     */
    @Argument(required = true)
    String ipAddress;

    /**
     * Instantiates a new NetworkDeviceDiscoverCommand.
     *
     * @param networkDeviceService the network device service
     */
    public NetworkDeviceDiscoverCommand(NetworkDeviceService networkDeviceService) {
        this.networkDeviceService = networkDeviceService;
    }

    @Override
    protected Object doExecute() throws Exception {
        networkDeviceService.discover(IpAddress.valueOf(ipAddress));
        return null;
    }
}
