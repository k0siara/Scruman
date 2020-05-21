package com.scruman.ui.views.backlog;

import com.scruman.StringUtils;
import com.scruman.app.HasLogger;
import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.Status;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.StoryService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.components.navigation.bar.AppBar;
import com.scruman.ui.views.Home;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Sprint Backlog")
@Route(value = "backlog", layout = MainLayout.class)
public class SprintBacklog extends StoriesView implements HasUrlParameter<String>, BeforeEnterObserver, HasLogger {

    private Long sprintId;

    @Autowired
    public SprintBacklog(StoryService storyService, SprintService sprintService, ProjectService projectService) {
        super(storyService, sprintService, projectService);
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

            ComboBox<Project> userProjects = MainLayout.get().getAppBar().getUserProjectsComboBox();
            if (!userProjects.isEmpty()) {
                Project currentProject = userProjects.getValue();
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
}
