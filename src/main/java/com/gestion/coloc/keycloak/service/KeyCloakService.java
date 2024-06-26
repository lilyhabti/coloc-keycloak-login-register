package com.gestion.coloc.keycloak.service;

import com.gestion.coloc.crud.models.User;
import com.gestion.coloc.crud.repositories.UserRepository;
import com.gestion.coloc.keycloak.config.Credentials;
import com.gestion.coloc.keycloak.config.KeycloakConfig;
import com.gestion.coloc.keycloak.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gestion.coloc.keycloak.config.KeycloakConfig.realm;

@AllArgsConstructor
@Service
public class KeyCloakService {

    @Autowired
    private final UserRepository userRepository;


    public void addUser(UserDTO userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setEmail(userDTO.getEmailId());
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        User userBd = new User();
        userBd.setUsername(userDTO.getUserName());
        userBd.setEmail(userDTO.getEmailId());
        userBd.setPassword(userDTO.getPassword());
        userBd.setProfilePic("");
        userBd.setRole(userDTO.getRoleName());

        // Create the user
        UsersResource instance = getInstance();
        Response response = instance.create(user);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: HTTP error code: " + response.getStatus());
        }

        userRepository.save(userBd);

        // Get the user's ID
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // Assign the role to the user
        assignRoleToUser(userId, userDTO.getRoleName());
    }

    private void assignRoleToUser(String userId, String roleName) {
        RealmResource realmResource =  KeycloakConfig.getInstance().realm(realm);
        RolesResource rolesResource = realmResource.roles();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;

    }

    public void updateUser(String userId, UserDTO userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    public void deleteUser(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        return KeycloakConfig.getInstance().realm(realm).users();
    }


}

