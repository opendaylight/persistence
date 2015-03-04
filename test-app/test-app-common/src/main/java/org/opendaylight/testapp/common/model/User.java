/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.testapp.common.type.Email;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.yangtools.concepts.Identifiable;

import com.google.common.base.MoreObjects;

/**
 * User.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class User implements Identifiable<Username> {
    /*
     * This class is not serializable for security reasons; to avoid serializing the password.
     */

    private final Username username;
    private Password password;
    private Email email;
    private String description;
    private boolean enabled;

    /**
     * Creates an user
     * 
     * @param username username
     */
    public User(@Nonnull Username username) {
        this.username = username;
    }

    @Override
    public Username getIdentifier() {
        return this.username;
    }

    /**
     * @return the password
     */
    public Password getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(@Nullable Password password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public Email getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(@Nullable Email email) {
        this.email = email;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("username", this.username).add("password", this.password)
                .add("email", this.email).add("description", this.description).add("isEnabled", this.enabled)
                .toString();
    }
}
