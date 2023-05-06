package com.foottech.foodproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER,  //default is FetchType.LAZY
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // to prevent JSON: Infinite recursion
    private List<Employee> employees;
}
