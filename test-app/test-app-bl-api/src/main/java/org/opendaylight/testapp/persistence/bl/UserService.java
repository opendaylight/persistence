package org.opendaylight.testapp.persistence.bl;

import java.util.List;

import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.type.Email;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;

/**
 * Provides all the methods related to a user.
 * Provides methods to create a user, authenticate a user,
 * disable a user, get the list of all enabled and disabled users. 
 */
public interface UserService {

    /**
     * Creates a user.
     * 
     * @param username username
     * @param password password
     * @param email user's e-mail
     * @return the created user
     * @throws DuplicateException if a user with the given {@code username} already exists
     */
    public User signUp(Username username, Password password, Email email) throws Exception; // Change to DuplicateException later

    /**
     * Authenticates a user.
     * 
     * @param username username
     * @param password password
     * @return the user if the authentication is valid and the user is enabled
     */
    public User signIn(Username username, Password password);

    /**
     * Disables a user.
     * 
     * @param id id of the user to disable
     * @return the updated user
     */
    public User disable(Id<User, Username> id);

    /**
     * Gets all enabled users.
     * 
     * @return enabled users
     */
    public List<User> getEnabled();

    /**
     * Gets all disabled users.
     * 
     * @return disabled users
     */
    public List<User> getDisabled();
}
