package com.epam.esm.controller.security;

import com.epam.esm.model.dto.UserDTO;

public class RegistrationBody {

    private UserDTO userDTO;
    private String password;

    public RegistrationBody() {
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
