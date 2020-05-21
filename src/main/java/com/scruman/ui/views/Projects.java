package com.scruman.ui.views;

import com.scruman.ui.MainLayout;
import com.scruman.ui.ProjectCard;
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

@PageTitle("Projects")
@Route(value = "projects", layout = MainLayout.class)
public class Projects extends ViewFrame {

    private static final String CLASS_NAME = "projects";

    public Projects() {
        FlexBoxLayout header = createHeader(VaadinIcon.RECORDS, "Search Projects");

        ProjectCard projectCard = new ProjectCard();
        ProjectCard projectCard2 = new ProjectCard();
        ProjectCard projectCard3 = new ProjectCard();

//        Div card = new Div();
//        card.setHeight("200px");
//        card.setWidth("200px");
//
//        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
//        UIUtils.setBorderRadius(BorderRadius.S, card);
//        UIUtils.setShadow(Shadow.XS, card);
//
        FlexBoxLayout project = new FlexBoxLayout(projectCard);
//        project.setFlexDirection(FlexDirection.COLUMN);
        project.setPadding(Bottom.XL, Left.RESPONSIVE_L);

        FlexBoxLayout project2 = new FlexBoxLayout(projectCard2);
//        project.setFlexDirection(FlexDirection.COLUMN);
        project2.setPadding(Bottom.XL, Left.RESPONSIVE_L);

        FlexBoxLayout project3 = new FlexBoxLayout(projectCard3);
//        project.setFlexDirection(FlexDirection.COLUMN);
        project3.setPadding(Bottom.XL, Left.RESPONSIVE_L);

        FlexBoxLayout content = new FlexBoxLayout(project, project2, project3);
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X, Bottom.RESPONSIVE_M);
//        content.setAlignItems(FlexComponent.Alignment.CENTER);
//        content.setFlexDirection(FlexDirection.COLUMN);
        setViewContent(content);
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