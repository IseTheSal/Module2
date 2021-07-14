package com.epam.esm.service;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;

import java.util.Optional;

/**
 * Interface extends {@link User} service functionality
 *
 * @author Illia Aheyeu
 */
public interface UserService extends CommonEntityService<UserDTO> {

    UserDTO create(UserDTO user, String password);

    UserDTO findByLoginAndPassword(String login, String password);

    Optional<User> findByLogin(String login);

    User findByLoginOrThrow(String login);
}
