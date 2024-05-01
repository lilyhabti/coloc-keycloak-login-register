package com.gestion.coloc.crud.services;


import com.gestion.coloc.crud.models.User;

public interface UserService {
    User getUserByUsername(String username);
    User saveOrUpdateUser(User user);
    User login(String username, String password);
    User signuUser(String username, User newUserDetails);

}
