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
import org.opendaylight.testapp.common.type.Email;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.persistence.bl.UserService;

/**
 * The User signUp command.
 */
@Command(name = "sign-up", scope = "user", description = "UserService")
public class UserSignUpCommand extends OsgiCommandSupport {
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
     * The Email.
     */
    @Argument(required = true, index = 2)
    String email;

    /**
     * Instantiates a new UserSignUpCommand.
     *
     * @param userService the user service
     */
    public UserSignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Username username = Username.valueOf(this.username);
        Password password = Password.valueOf(this.password);
        Email email = Email.valueOf(this.email);
        userService.signUp(username, password, email);
        return null;
    }
}
