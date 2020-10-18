package com.scruman.ui.views.backlog;


import com.scruman.app.HasLogger;
import com.scruman.backend.entity.Project;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.SprintService;
import com.scruman.backend.service.StoryService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.views.Home;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Product Backlog")
@Route(value = "backlog", layout = MainLayout.class)
public class ProductBacklog extends StoriesView implements BeforeEnterObserver, HasLogger {

    @Autowired
    public ProductBacklog(UserService userService, ProjectService projectService, SprintService sprintService, StoryService storyService) {
        super(userService, storyService, sprintService, projectService);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        getLogger().debug("ProductBacklog onAttach call");
        loadData();
    }

    private void loadData() {
        getLogger().debug("ProductBacklog loadData call");

        Project currentProject = userService.getCurrentUser().getLastOpenedProject();
        if (currentProject != null) {
            getLogger().debug("ProductBacklog userProjects notEmpty");
            grid.setItems(storyService.findAllByProjectAndStatus(currentProject.getId(), "None"));
        } else {
            getLogger().debug("ProductBacklog userProjects empty");
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        getLogger().debug("ProductBacklog beforeEnter call");

        if (!SecurityUtils.isUserLoggedIn()) {
            UI.getCurrent().navigate(Home.class);
        }
    }
}
