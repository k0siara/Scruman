package com.scruman.ui.views.projects;

import com.scruman.app.HasLogger;
import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.User;
import com.scruman.backend.service.ProjectService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.views.ViewFrame;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.components.ListItem;
import com.scruman.ui.layout.size.*;
import com.scruman.ui.util.IconSize;
import com.scruman.ui.util.LumoStyles;
import com.scruman.ui.util.TextColor;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BorderRadius;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.util.css.Shadow;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Projects")
@Route(value = "projects", layout = MainLayout.class)
public class Projects extends ViewFrame implements HasLogger {

    private static final String CLASS_NAME = "projects";

    private UserService userService;
    private ProjectService projectService;

    private NewProjectDialog newProjectDialog = new NewProjectDialog();

    @Autowired
    public Projects(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;

        FlexBoxLayout header = createHeader(VaadinIcon.RECORDS, "Search Projects");

        createCurrentUserProjects();
    }

    private FlexBoxLayout createHeader(VaadinIcon icon, String title) {
        FlexBoxLayout header = new FlexBoxLayout(
                UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon),
                UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Bottom.L, Horizontal.RESPONSIVE_L);
        header.setSpacing(Right.L);
        return header;
    }

    private void createCurrentUserProjects() {
        User currentUser = userService.getCurrentUser();
        List<Project> currentUserProjects = projectService.getUserProjects(currentUser.getId());
        getLogger().debug("Found " + currentUserProjects.size() + " projects..");

        FlexBoxLayout content = new FlexBoxLayout();

        for (Project p : currentUserProjects) {
            content.add(new ProjectCard(p));
        }

        NewProjectCard newProjectCard = new NewProjectCard(e -> newProjectDialog.open());
        content.add(newProjectCard);

        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X, Bottom.RESPONSIVE_M);
        setViewContent(content);
    }

    private Component createLogs() {
        FlexBoxLayout header = createHeader(VaadinIcon.EDIT, "Logs");
        Tabs tabs = new Tabs();
        for (String label : new String[]{"All", "Transfer", "Security",
                "Change"}) {
            tabs.add(new Tab(label));
        }

        Div items = new Div(
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.EXCHANGE),
                        "Transfers (October)", "Generated Oct 31, 2018"),
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.SHIELD),
                        "Security Log", "Updated 16:31 CET"));
        items.addClassNames(LumoStyles.Padding.Vertical.S);

        Div card = new Div(tabs, items);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);

        FlexBoxLayout logs = new FlexBoxLayout(header, card);
        logs.addClassName(CLASS_NAME + "__logs");
        logs.setFlexDirection(FlexDirection.COLUMN);
        logs.setPadding(Bottom.XL, Right.RESPONSIVE_L);
        return logs;
    }
}