package com.scruman.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sprints")
@NoArgsConstructor
@AllArgsConstructor
public class Sprint extends AbstractEntity{

    private String title;

    private String description;

    private LocalDate beginDate;

    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "sprint", fetch = FetchType.EAGER)
    private Set<Story> stories = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;


}
