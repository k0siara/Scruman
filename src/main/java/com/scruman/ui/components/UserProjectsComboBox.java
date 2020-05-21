package com.scruman.ui.components;

import com.scruman.backend.entity.Project;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserProjectsComboBox extends ComboBox<Project> {

    public UserProjectsComboBox() {
        createContent();
    }

    private void createContent() {
        setItemLabelGenerator(Project::getName);
        setPlaceholder("Choose project");
        setAllowCustomValue(false);
        setPreventInvalidInput(true);
    }

    public void setProjects(List<Project> projects) {
        clear();
        setItems(projects);
    }
}
