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

    public String title;

    public String description;

    public LocalDate beginDate;

    public LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    public Status status;

    @OneToMany(mappedBy = "sprint", fetch = FetchType.EAGER)
    public Set<Story> stories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project project;
}
