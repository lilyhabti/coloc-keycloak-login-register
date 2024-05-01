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
@Table(name = "flatsharesapps")
@Entity
public class FlatShareApplication {
    @Id
    @GeneratedValue
    private Long idApp;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @ManyToOne
    @JoinColumn(name="applicant_id")
    private User applicant;
    @ManyToOne
    @JoinColumn(name="flatShare_id")
    private FlatShare flatShare;

    @JsonIgnore
    public User getApplicant() {
        return applicant;
    }

    @JsonIgnore
    public FlatShare getFlatShare() {
        return flatShare;
    }
}
