package com.gestion.coloc.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String userName;
    private String emailId;
    private String password;
}
