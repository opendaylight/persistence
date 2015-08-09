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
import org.opendaylight.testapp.common.type.*;
import org.opendaylight.testapp.persistence.bl.UserService;

/**
 * The User signIn command.
 */
@Command(name = "sign-in", scope = "user", description = "UserService")
public class UserSignInCommand extends OsgiCommandSupport {
    private final UserService userService;

    /**
     * The Username.
     */
    @Argument(required = true, index = 0)
    String username;

    /**
     * The Password.
     */
    @Argument(required = true, index = 1)
    String password;

    /**
     * Instantiates a new UserSignInCommand.
     *
     * @param userService the user service
     */
    public UserSignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Username username = Username.valueOf(this.username);
        Password password = Password.valueOf(this.password);
        userService.signIn(username, password);
        return null;
    }
}
