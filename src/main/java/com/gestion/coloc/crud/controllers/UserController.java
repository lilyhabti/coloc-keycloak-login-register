package com.gestion.coloc.crud.controllers;


import com.gestion.coloc.crud.models.User;
import com.gestion.coloc.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping
    public ResponseEntity<User> createOrUpdateUser(@RequestBody User user,@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(userService.signuUser(username,user));
    }

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveOrUpdateUser(user));
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(user); // Connexion réussie
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Identifiants invalides
        }
    }
}