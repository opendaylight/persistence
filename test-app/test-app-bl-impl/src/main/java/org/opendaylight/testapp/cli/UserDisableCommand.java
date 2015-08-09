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
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.persistence.bl.UserService;

/**
 * The User disable command.
 */
@Command(name = "disable", scope = "user", description = "UserService")
public class UserDisableCommand extends OsgiCommandSupport {
    private final UserService userService;

    /**
     * The Username.
     */
    @Argument(required = true, index = 0)
    String username;

    /**
     * Instantiates a new UserDisableCommand.
     *
     * @param userService the user service
     */
    public UserDisableCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Username username = Username.valueOf(this.username);
        Id<User, Username> id = Id.valueOf(username);
        userService.disable(id);
        return null;
    }
}
