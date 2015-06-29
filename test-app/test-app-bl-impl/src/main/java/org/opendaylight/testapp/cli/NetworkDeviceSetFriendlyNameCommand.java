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
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;

/**
 * The NetworkDevice setFriendlyName command.
 */
@Command(name = "set-friendly-name", scope = "networkdevice", description = "NetworkDeviceService")
public class NetworkDeviceSetFriendlyNameCommand extends OsgiCommandSupport {
    private final NetworkDeviceService networkDeviceService;

    /**
     * The Serial number.
     */
    @Argument(required = true, index = 0)
    String serialNumber;

    /**
     * The Friendly name.
     */
    @Argument(required = true, index = 1)
    String friendlyName;

    /**
     * Instantiates a new NetworkDeviceSetFriendlyNameCommand.
     *
     * @param networkDeviceService the network device service
     */
    public NetworkDeviceSetFriendlyNameCommand(NetworkDeviceService networkDeviceService) {
        this.networkDeviceService = networkDeviceService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Id<NetworkDevice, SerialNumber> id = Id.valueOf(SerialNumber.valueOf(serialNumber));
        networkDeviceService.setFriendlyName(id, friendlyName);
        return null;
    }
}
