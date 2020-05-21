package com.scruman.ui.components;

import com.scruman.backend.entity.Project;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.List;

public class UserProjectsComboBox extends ComboBox<Project> {

    public static Project NEW_PROJECT;

    public UserProjectsComboBox() {
        initNewProject();
        createContent();
    }

    private void initNewProject() {
        NEW_PROJECT = new Project();
        NEW_PROJECT.setName("+ New project");
        NEW_PROJECT.setId(0L);
    }

    private void createContent() {
        setItemLabelGenerator(Project::getName);
        setPlaceholder("Choose project");
        setAllowCustomValue(false);
        setPreventInvalidInput(true);
        setItems(NEW_PROJECT);
    }

    public void setProjects(List<Project> projects) {
        clear();

        projects.add(NEW_PROJECT);
        setItems(projects);
    }
}
