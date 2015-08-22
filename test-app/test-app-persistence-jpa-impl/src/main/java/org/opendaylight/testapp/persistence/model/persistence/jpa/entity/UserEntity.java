/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.testapp.persistence.model.persistence.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.opendaylight.testapp.common.type.Email;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;

import com.google.common.base.Objects;


/**
 * User.
 *
 * @author Fabiel Zuniga
 */
@Entity
@Table(name = "users")
public final class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private boolean enabled;

    /**
     * This constructor is provided because of a restriction imposed by JPA and should not be used.
     */
    @Deprecated
    public UserEntity() {

    }

    /**
     * Creates an user
     *
     * @param username username
     */
    public UserEntity(Username username) {
        this.username = username.getValue();
    }

    /**
     * Returns the id
     *
     * @return the id
     */
    public Username getId() {
        if (this.username != null) {
            return Username.valueOf(this.username);
        }
        return null;
    }

    /**
     * @return the password
     */
    public Password getPassword() {
        Password value = null;
        if (this.password != null) {
            value = Password.valueOf(this.password);
        }
        return value;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(Password password) {
        this.password = null;
        if (password != null) {
            this.password = password.getValue();
        }
    }

    /**
     * @return the email
     */
    public Email getEmail() {
        Email value = null;
        if (this.email != null) {
            value = Email.valueOf(this.email);
        }
        return value;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(Email email) {
        this.email = null;
        if (email != null) {
            this.email = email.getValue();
        }
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
    public void setDescription(String description) {
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
		return Objects.toStringHelper(this.getClass()).add("id", getId())
				.add("password", this.password).add("email", this.email)
				.add("description", this.description)
				.add("isEnabled", this.enabled).toString();
	}
}
