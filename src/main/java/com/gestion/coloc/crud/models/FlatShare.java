package com.gestion.coloc.crud.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flatshares")
@Entity
public class FlatShare {
    @Id
    @GeneratedValue
    private Long idFlat;
    private String descriptionF;
    private String address;
    private int numberOfRooms;
    private int numberOfRoomsOccupied;
    private String flatPic;

    @OneToOne(cascade = CascadeType.ALL)
    private User owner;
    @OneToMany(mappedBy = "flatShareColocs")
    private List<User> roomates = new ArrayList<>();
    @OneToMany(mappedBy = "flatShareExpenses")
    private List<Expense> expenses = new ArrayList<>();
    @OneToMany(mappedBy = "flatShareTasks")
    private List<Task> tasks = new ArrayList<>();
    @OneToMany(mappedBy = "flatShareCate")
    private List<Category> categories = new ArrayList<>();


}
