package com.epam.esm.service;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;

/**
 * Interface extends {@link User} service functionality
 *
 * @author Illia Aheyeu
 */
public interface UserService extends CommonEntityService<UserDTO> {

    UserDTO create(UserDTO user, String password);

    UserDTO findByLoginAndPassword(String login, String password);
}
