/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.testapp.cli;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;

import java.util.List;

/**
 * The NetworkDevice getByLocation command.
 */
@Command(name = "get-by-location", scope = "networkdevice", description = "NetworkDeviceService")
public class NetworkDeviceGetByLocationCommand extends OsgiCommandSupport {
    private final NetworkDeviceService networkDeviceService;

    /**
     * The Location.
     */
    @Argument(required = true)
    String location;

    /**
     * Instantiates a new NetworkDeviceGetByLocationCommand.
     *
     * @param networkDeviceService the network device service
     */
    public NetworkDeviceGetByLocationCommand(NetworkDeviceService networkDeviceService) {
        this.networkDeviceService = networkDeviceService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Location location = Location.valueOf(this.location);
        List<NetworkDevice> devices = networkDeviceService.getByLocation(location);
        StringBuilder stringBuilder = new StringBuilder();
        for (NetworkDevice device : devices) {
            stringBuilder.append(device.toString());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
