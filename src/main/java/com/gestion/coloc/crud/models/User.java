package com.gestion.coloc.crud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long idUser;
    private String username;
    private String email;
    private String password;
    private String profilePic;
    private String role;

    @ManyToOne
    @JoinColumn(name="flatShareCol_id")
    private FlatShare flatShareColocs;

    @JsonIgnore
    public FlatShare getFlatShareColocs() {
        return flatShareColocs;
    }


}
