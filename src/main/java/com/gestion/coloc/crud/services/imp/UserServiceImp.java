package com.gestion.coloc.crud.services.imp;


import com.gestion.coloc.crud.models.User;
import com.gestion.coloc.crud.repositories.UserRepository;
import com.gestion.coloc.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveOrUpdateUser(User user) {
        userRepository.save(user);
        return user;
    }
    public User signuUser(String username, User newUserDetails) {
        // Vérifie si l'utilisateur existe déjà dans la base de données
        User existingUser = userRepository.findByUsername(username);

        // Si l'utilisateur n'existe pas, renvoie null
        if (existingUser == null) {
            return null;
        }

        // Met à jour les détails de l'utilisateur avec les nouvelles valeurs
        existingUser.setUsername(newUserDetails.getUsername());
        existingUser.setPassword(newUserDetails.getPassword());
        existingUser.setEmail(newUserDetails.getEmail());
        existingUser.setProfilePic(newUserDetails.getProfilePic());

        // Enregistre les modifications dans la base de données
        userRepository.save(existingUser);

        return existingUser;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Connexion réussie
        } else {
            return null; // Identifiants invalides
        }
    }
}
