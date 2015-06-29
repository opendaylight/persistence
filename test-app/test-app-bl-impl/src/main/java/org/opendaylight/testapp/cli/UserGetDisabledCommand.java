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
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.persistence.bl.UserService;

import java.util.List;

/**
 * The User getDisabled command.
 */
@Command(name = "get-disabled", scope = "user", description = "UserService")
public class UserGetDisabledCommand extends OsgiCommandSupport {
    private final UserService userService;

    /**
     * Instantiates a new UserGetDisabledCommand.
     *
     * @param userService the user service
     */
    public UserGetDisabledCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Object doExecute() throws Exception {
        List<User> users = userService.getDisabled();
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(user.toString());
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
