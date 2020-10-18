package com.scruman.ui.views.projects;

import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.User;
import com.scruman.backend.entity.UserProject;
import com.scruman.backend.entity.UserProjectId;
import com.scruman.backend.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewProjectDialog extends Dialog {

    private TextField name = new TextField("Name");
    private TextArea description = new TextArea("Description");
    private ComboBox<User> userComboBox = new ComboBox<>("Choose member");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private List<User> users = new ArrayList<>();
    private VerticalLayout usersLayout = new VerticalLayout();

    private UserService userService;

    public NewProjectDialog(UserService userService) {
        this.userService = userService;
        reset();
        createForm();
    }

    private void createForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2));

        name.setPlaceholder("Name of your project");
        description.setPlaceholder("What's the project about?");
        userComboBox.setPlaceholder("Add some members to the project...");

        userComboBox.setItemLabelGenerator(User::getName);
        userComboBox.addValueChangeListener(event -> {
            User user = event.getValue();
            if (event.getValue() != null && !users.contains(user)) {
                usersLayout.add(new Label(user.getName()));
                users.add(user);
            }
        });

        formLayout.add(name, 1);
        formLayout.add(description, 1);
        formLayout.add(userComboBox, 2);
        formLayout.add(usersLayout, 2);

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);
        save.getStyle().set("marginRight", "10px");

        add(formLayout);
        add(actions);
    }

    public void reset() {
        name.clear();
        description.clear();
        userComboBox.clear();
        users.clear();

        List<User> allUsers = userService.getAll();
        allUsers.removeIf(user -> user.getUsername().equals(userService.getCurrentUser().getUsername()));

        userComboBox.setItems(allUsers);
    }

    public void onSave(ComponentEventListener<ClickEvent<Button>> listener) {
        save.addClickListener(listener);
    }

    public void onCancel(ComponentEventListener<ClickEvent<Button>> listener) {
        cancel.addClickListener(listener);
    }

    public Project getProject() {
        Project project = new Project();
        project.setName(name.getValue());
        project.setDescription(description.getValue());

        users.add(userService.getCurrentUser());
        Set<UserProject> userProjects = users.stream()
                .map(user -> new UserProject(new UserProjectId(), user, project, 10))
                .collect(Collectors.toSet());
        project.setUserProjects(userProjects);
        return project;
    }
}
