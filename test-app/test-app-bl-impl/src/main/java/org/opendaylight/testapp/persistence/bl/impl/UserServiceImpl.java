package org.opendaylight.testapp.persistence.bl.impl;


import java.util.List;

import org.opendaylight.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.persistence.util.common.DuplicateException;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.type.Email;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.persistence.PersistenceService;
import org.opendaylight.testapp.persistence.bl.UserService;


/**
 * @author Fabiel Zuniga
 */
public class UserServiceImpl implements UserService {

    private final PersistenceService persistenceService;
    private final Logger logger;

    public UserServiceImpl(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        this.logger = LoggerFactory.getLogger(getClass());
    }
    

    @Override
    public User signUp(Username username, Password password, Email email) throws DuplicateException {
        Id<User, Username> id = Id.valueOf(username);
        User user = new User(id.getValue());
        user.setPassword(password);
        user.setEmail(email);
        user.setEnabled(true);

        // TODO: Verify if the user already exists

        try {
            this.persistenceService.user().store(user);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to create user " + user, e);
            throw new RuntimeException("Unable to sign up");
        }

        return user;
    }

    @Override
    public User signIn(Username username, Password password) {
        Id<User, Username> id = Id.valueOf(username);
        User user = null;
        try {
            user = this.persistenceService.user().get(id);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve user with id " + id, e);
            throw new RuntimeException("Unable to retrieve user with id " + id);
        }

        if (password!=null && password.equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public User disable(Id<User, Username> id) {
        User user = null;
        try {
            user = this.persistenceService.user().get(id);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve user with id " + id, e);
            throw new RuntimeException("Unable to disable user");
        }

        user.setEnabled(false);

        try {
            this.persistenceService.user().store(user);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to update user " + user, e);
            throw new RuntimeException("Unable to disable user");
        }

        return user;
    }

    @Override
    public List<User> getEnabled() {
        UserFilter filter = UserFilter.byEnabledStatus(true);
        try {
            return this.persistenceService.user().find(filter);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve users with filter " + filter, e);
            throw new RuntimeException("Unable to retrieve users");
        }
    }

    @Override
    public List<User> getDisabled() {
        UserFilter filter = UserFilter.byEnabledStatus(false);
        try {
            return this.persistenceService.user().find(filter);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve users with filter " + filter, e);
            throw new RuntimeException("Unable to retrieve users");
        }
    }
}

