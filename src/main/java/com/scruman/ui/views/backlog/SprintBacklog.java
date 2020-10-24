package com.scruman.ui.views.backlog;

import com.scruman.StringUtils;
import com.scruman.app.HasLogger;
import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.Status;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.StoryService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.components.navigation.bar.AppBar;
import com.scruman.ui.layout.size.Bottom;
import com.scruman.ui.layout.size.Horizontal;
import com.scruman.ui.layout.size.Top;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BoxSizing;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.views.Home;
import com.scruman.ui.views.sprint_retrospective.SprintRetrospectiveView;
import com.scruman.ui.views.sprint_review.SprintReviewView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Sprint Backlog")
@Route(value = "backlog", layout = MainLayout.class)
public class SprintBacklog extends StoriesView implements HasUrlParameter<String>, BeforeEnterObserver, HasLogger {

    private Long sprintId;

    @Autowired
    public SprintBacklog(UserService userService, StoryService storyService, SprintService sprintService, ProjectService projectService) {
        super(userService, storyService, sprintService, projectService);
    }

    @Override
    public void setViewContent(Component... components) {
        content.removeAll();

        FlexBoxLayout contentLayout = new FlexBoxLayout(createGrid(),
                new HorizontalLayout(createAddButton(), createSprintReviewButton(), createSprintRetrospectiveButton()));
        contentLayout.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X, Bottom.RESPONSIVE_M);
        contentLayout.setFlexDirection(FlexDirection.COLUMN);
        contentLayout.setBoxSizing(BoxSizing.BORDER_BOX);
        contentLayout.setHeightFull();

        content.add(contentLayout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        getLogger().debug("SprintBacklog setParameter call");

        this.sprintId = Long.valueOf(parameter);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        getLogger().debug("SprintBacklog onAttach call");


        initAppBar();
        filter();
    }

    private void initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();

        List<Status> availableStatuses = storyService.getAvailableStatuses();
        for (Status s : availableStatuses) {
            if (!s.getName().equals("None")) {
                appBar.addTab(s.getName());
            }
        }

        appBar.addTabSelectionListener(e -> {
            filter();
            detailsDrawer.hide();
        });

        appBar.centerTabs();
    }

    private void filter() {
        getLogger().debug("SprintBacklog filter call");


        Tab selectedTab = MainLayout.get().getAppBar().getSelectedTab();
        if (selectedTab != null) {
            String status = StringUtils.capitalize(selectedTab.getLabel());

            Project currentProject = userService.getCurrentUser().getLastOpenedProject();
            if (currentProject != null) {
                grid.setItems(storyService.findAllByProjectAndSprintAndStatus(currentProject.getId(), sprintId, status));
            }
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        getLogger().debug("SprintBacklog beforeEnter call");

        if (!SecurityUtils.isUserLoggedIn()) {
            UI.getCurrent().navigate(Home.class);
        }

    }

    private Component createSprintReviewButton() {
        Button button = UIUtils.createButton("Sprint Review");
        button.addClickListener(this::goToSprintReview);
        return button;
    }

    private void goToSprintReview(ClickEvent<Button> event) {
        UI.getCurrent().navigate(SprintReviewView.class, sprintId.toString());
        UI.getCurrent().getPage().reload();
    }

    private Component createSprintRetrospectiveButton() {
        Button button = UIUtils.createButton("Sprint Retrospective");
        button.addClickListener(this::goToSprintRetrospective);
        return button;
    }

    private void goToSprintRetrospective(ClickEvent<Button> event) {
        UI.getCurrent().navigate(SprintRetrospectiveView.class, sprintId.toString());
        UI.getCurrent().getPage().reload();
    }
}
