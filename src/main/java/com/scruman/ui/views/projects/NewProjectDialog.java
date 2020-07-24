package com.scruman.ui.views.projects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class NewProjectDialog extends Dialog {

    private TextField name = new TextField("Name");
    private TextArea description = new TextArea("Name");

    public NewProjectDialog() {
        createForm();
    }

    private void createForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2));


        name.setPlaceholder("Name of your project");
        description.setPlaceholder("What's the project about?");

        formLayout.add(name, 1);
        formLayout.add(description, 1);

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);
        save.getStyle().set("marginRight", "10px");

        add(formLayout);
        add(actions);
    }
}
