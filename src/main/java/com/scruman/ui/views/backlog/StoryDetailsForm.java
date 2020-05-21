package com.scruman.ui.views.backlog;

import com.scruman.backend.entity.Sprint;
import com.scruman.backend.entity.Status;
import com.scruman.backend.entity.Story;
import com.scruman.backend.entity.User;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.StoryService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.components.IntegerOnlyTextField;
import com.scruman.ui.util.LumoStyles;
import com.scruman.ui.util.UIUtils;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

public class StoryDetailsForm extends FormLayout {

    private StoryService storyService;
    private SprintService sprintService;
    private ProjectService projectService;

    private Story story;

    private TextField title = new TextField();
    private TextArea shortDescription = new TextArea();
    private TextArea description = new TextArea();
    private TextArea acceptanceCriteria = new TextArea();
    private IntegerOnlyTextField storyPoints = new IntegerOnlyTextField();
    private ComboBox<Status> status = new ComboBox<>();
    private ComboBox<Sprint> sprint = new ComboBox<>();
    private ComboBox<User> assignedTo = new ComboBox<>();
    private TextField addedBy = new TextField();

    private BeanValidationBinder<Story> binder = new BeanValidationBinder<>(Story.class);

    public StoryDetailsForm(StoryService storyService, SprintService sprintService, ProjectService projectService) {
        this.storyService = storyService;
        this.sprintService = sprintService;
        this.projectService = projectService;

        createContent();
        setBinder();
    }

    private void createContent() {
        title.setWidthFull();
        shortDescription.setWidthFull();
        description.setWidthFull();
        acceptanceCriteria.setWidthFull();
        storyPoints.setWidthFull();

        status.setItemLabelGenerator(Status::getName);
        status.setItems(storyService.getAvailableStatuses());
        status.setWidthFull();

        sprint.setItemLabelGenerator(Sprint::getTitle);
        sprint.setWidthFull();

        assignedTo.setItemLabelGenerator(User::getName);
        assignedTo.setWidthFull();

        addedBy.setReadOnly(true);
        addedBy.setWidthFull();

        // Form layout
        addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));

        FormLayout.FormItem titleItem = addFormItem(title, "Title");
        FormLayout.FormItem shortDescriptionItem = addFormItem(shortDescription, "Short description");
        FormLayout.FormItem descriptionItem = addFormItem(description, "Description");
        FormLayout.FormItem acceptanceCriteriaItem = addFormItem(acceptanceCriteria, "Acceptance criteria");
        UIUtils.setColSpan(2, titleItem, shortDescriptionItem, descriptionItem, acceptanceCriteriaItem);

        addFormItem(status, "Status");
        addFormItem(storyPoints, "Story points");

        FormLayout.FormItem sprintItem = addFormItem(sprint, "Sprint");
        FormLayout.FormItem assignedToItem = addFormItem(assignedTo, "Assigned to");
        FormLayout.FormItem addedByItem = addFormItem(addedBy, "Added by");
        UIUtils.setColSpan(2, sprintItem, assignedToItem, addedByItem);
    }

    private void setBinder() {
        binder.forField(title).bind(Story::getTitle, Story::setTitle);
        binder.forField(shortDescription).bind(Story::getShortDescription, Story::setShortDescription);
        binder.forField(description).bind(Story::getDescription, Story::setDescription);
        binder.forField(acceptanceCriteria).bind(Story::getAcceptanceCriteria, Story::setAcceptanceCriteria);
        binder.forField(storyPoints)
                .withConverter(new StringToIntegerConverter("Please enter a number"))
                .bind(Story::getStoryPoints, Story::setStoryPoints);
        binder.forField(status).bind(Story::getStatus, Story::setStatus);
        binder.forField(sprint).bind(Story::getSprint, Story::setSprint);
        binder.forField(assignedTo).bind(Story::getAssignedTo, Story::setAssignedTo);
        binder.forField(addedBy).bind(s -> s.getAddedBy().getName(), null);
    }

    public void setStory(Story story) {
        this.story = story;
        updateForm();

        binder.setBean(story);
    }

    public Story getStory() {
        return story;
    }

    private void updateForm() {
        title.setValue(story.getTitle());
        shortDescription.setValue(story.getShortDescription());
        description.setValue(story.getDescription());
        acceptanceCriteria.setValue(story.getAcceptanceCriteria());
        storyPoints.setValue(story.getStoryPoints().toString());

        status.setItems(storyService.getAvailableStatuses());
        status.setValue(story.getStatus());

        sprint.setItems(sprintService.findAllByProjectId(story.getProject().getId()));
        sprint.setValue(story.getSprint());

        assignedTo.setItems(projectService.getProjectMembers(story.getProject().getId()));
        assignedTo.setValue(story.getAssignedTo());

        addedBy.setValue(story.getAddedBy().getName());
    }

    public void clearForm() {
        story = new Story();
        Long currentProjectId = MainLayout.get().getAppBar().getUserProjectsComboBox().getValue().getId(); // TODO: 22-May-20 change fo db data

        title.setValue("");
        shortDescription.setValue("");
        description.setValue("");
        acceptanceCriteria.setValue("");
        storyPoints.setValue("0");

        status.setItems(storyService.getAvailableStatuses());
        status.setValue(status.getEmptyValue());

        sprint.setItems(projectService.getSprints(currentProjectId));
        sprint.setValue(sprint.getEmptyValue());

        assignedTo.setItems(projectService.getProjectMembers(currentProjectId));
        assignedTo.setValue(assignedTo.getEmptyValue());

        addedBy.setValue("");
    }

}
