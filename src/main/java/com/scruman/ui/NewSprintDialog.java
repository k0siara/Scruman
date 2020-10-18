package com.scruman.ui;

import com.scruman.backend.beans.CurrentUser;
import com.scruman.backend.entity.Sprint;
import com.scruman.backend.entity.Status;
import com.scruman.backend.service.StoryService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class NewSprintDialog extends Dialog {

    private TextField title = new TextField("Title");
    private TextArea description = new TextArea("Description");
    private DatePicker beginDate = new DatePicker("Begin date");
    private DatePicker endDate = new DatePicker("End date");
    private ComboBox<Status> statusComboBox = new ComboBox<>("Status");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private CurrentUser currentUser;
    private StoryService storyService;

    public NewSprintDialog(CurrentUser currentUser, StoryService storyService) {
        this.currentUser = currentUser;
        this.storyService = storyService;
        createForm();
    }

    private void createForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2));

        title.setPlaceholder("Sprint title");
        description.setPlaceholder("Describe the sprint...");
        beginDate.setPlaceholder("Add begin date");
        endDate.setPlaceholder("Add end date");
        statusComboBox.setPlaceholder("Choose status...");

        statusComboBox.setItems(storyService.getAvailableStatuses());
        statusComboBox.setItemLabelGenerator(Status::getName);

        formLayout.add(title, 2);
        formLayout.add(description, 2);
        formLayout.add(beginDate, 1);
        formLayout.add(endDate, 1);
        formLayout.add(statusComboBox, 2);

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, cancel);
        save.getStyle().set("marginRight", "10px");

        add(formLayout);
        add(actions);
    }

    public void onSave(ComponentEventListener<ClickEvent<Button>> listener) {
        save.addClickListener(listener);
    }

    public void onCancel(ComponentEventListener<ClickEvent<Button>> listener) {
        cancel.addClickListener(listener);
    }

    public Sprint getSprint() {
        Sprint sprint = new Sprint();
        sprint.title = title.getValue();
        sprint.description = description.getValue();
        sprint.beginDate = beginDate.getValue();
        sprint.endDate = endDate.getValue();
        sprint.status = statusComboBox.getValue();
        sprint.project = currentUser.get().getLastOpenedProject();
        return sprint;
    }
}
