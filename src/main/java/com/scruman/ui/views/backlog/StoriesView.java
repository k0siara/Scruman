package com.scruman.ui.views.backlog;

import com.scruman.backend.entity.Status;
import com.scruman.backend.entity.Story;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.StoryService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.components.Badge;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.components.ListItem;
import com.scruman.ui.components.detailsdrawer.DetailsDrawer;
import com.scruman.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.scruman.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.scruman.ui.layout.size.Bottom;
import com.scruman.ui.layout.size.Horizontal;
import com.scruman.ui.layout.size.Top;
import com.scruman.ui.layout.size.Vertical;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BoxSizing;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.util.css.lumo.BadgeColor;
import com.scruman.ui.views.SplitViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.time.format.DateTimeFormatter;

public class StoriesView extends SplitViewFrame {

    protected UserService userService;
    protected StoryService storyService;
    protected SprintService sprintService;
    protected ProjectService projectService;

    protected Grid<Story> grid;
    protected DetailsDrawer<Story> detailsDrawer;

    protected StoryDetailsForm storyDetailsForm;

    public StoriesView(UserService userService, StoryService storyService, SprintService sprintService, ProjectService projectService) {
        this.userService = userService;
        this.storyService = storyService;
        this.sprintService = sprintService;
        this.projectService = projectService;
        this.storyDetailsForm = new StoryDetailsForm(this.userService, storyService, sprintService, projectService);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createGrid(), createAddButton());
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X, Bottom.RESPONSIVE_M);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();

        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addItemDoubleClickListener(event -> showDetails(event.getItem()));
        grid.setDropMode(GridDropMode.BETWEEN);
        grid.setRowsDraggable(true);

        ComponentRenderer<Badge, Story> badgeRenderer = new ComponentRenderer<>(
                story -> {
                    Status status = story.getStatus();
                    Badge badge = new Badge(status.getName(), BadgeColor.NORMAL);
                    UIUtils.setTooltip(status.getName(), badge);
                    return badge;
                }
        );
        grid.addColumn(badgeRenderer)
                .setAutoWidth(true)
                .setFlexGrow(1)
                .setHeader("Status");

        grid.addColumn(new ComponentRenderer<>(this::createTitleInfo))
                .setHeader("Title")
                .setWidth("100px");

        grid.addColumn(new ComponentRenderer<>(this::createDescriptionInfo))
                .setHeader("Description")
                .setWidth("200px");

        grid.addColumn(new ComponentRenderer<>(this::createStoryPointsInfo))
                .setComparator(Story::getStoryPoints)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Story points")
                .setTextAlign(ColumnTextAlign.END);

        grid.addColumn(new ComponentRenderer<>(this::createCreatedAtInfo))
                .setAutoWidth(true)
                .setComparator(Story::getCreatedAt)
                .setFlexGrow(0)
                .setHeader("Created at");

        return grid;
    }

    private Component createTitleInfo(Story story) {
        ListItem item = new ListItem(story.getTitle());
        item.setPadding(Vertical.XS);
        return item;
    }

    private Component createStoryPointsInfo(Story story) {
        ListItem item = new ListItem(story.getStoryPoints().toString());
        item.setPadding(Vertical.XS);
        return item;
    }

    private Component createDescriptionInfo(Story story) {
        ListItem item = new ListItem(story.getDescription());
        item.setPadding(Vertical.XS);
        return item;
    }

    private Component createCreatedAtInfo(Story story) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDateTime = story.getCreatedAt().format(dateTimeFormatter);
        ListItem item = new ListItem(formattedDateTime);
        item.setPadding(Vertical.XS);
        return item;
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer<>(DetailsDrawer.Position.RIGHT);

        // Header
        DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Story details");
        detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addSaveListener(e -> {
            storyService.save(storyDetailsForm.getStory());
            UI.getCurrent().getPage().reload();
        });
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void createNew() {
        storyDetailsForm.clearForm();
        openDetailsDrawer();
    }

    private void showDetails(Story story) {
        storyDetailsForm.setStory(story);
        openDetailsDrawer();
    }

    private void openDetailsDrawer() {
        detailsDrawer.setContent(storyDetailsForm);
        detailsDrawer.show();
    }

    private Component createAddButton() {
        Button addButton = UIUtils.createPrimaryButton("Add new");
        addButton.addClickListener(event -> createNew());
        return addButton;
    }

}
