package com.scruman.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "stories")
@NoArgsConstructor
@AllArgsConstructor
public class Story extends AbstractEntity {

    private String title;

    private Long number;

    private String shortDescription;

    private String description;

    private Integer storyPoints;

    private String acceptanceCriteria;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedTo;
}
