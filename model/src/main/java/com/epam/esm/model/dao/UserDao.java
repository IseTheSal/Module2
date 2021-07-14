package com.epam.esm.model.dao;

import com.epam.esm.model.entity.UserRole;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.Optional;

/**
 * Interface extends {@link Tag} database functionality
 *
 * @author Illia Aheyeu
 */
public interface UserDao extends CommonDao<User> {

    User create(User user);

    Optional<User> findByLogin(String login);

    Optional<UserRole> findRoleByName(String name);

}
