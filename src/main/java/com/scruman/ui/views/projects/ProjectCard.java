package com.scruman.ui.views.projects;

import com.scruman.AppConstants;
import com.scruman.backend.entity.Project;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.layout.size.Bottom;
import com.scruman.ui.layout.size.Left;
import com.scruman.ui.util.LumoStyles;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BorderRadius;
import com.scruman.ui.util.css.Shadow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ProjectCard extends FlexBoxLayout {

    private static final String CLASS_NAME = "project_card";

    private Label label = new Label();
    private Button optionsButton = new Button(VaadinIcon.ELLIPSIS_DOTS_H.create());

    public ProjectCard(Project project) {
        Div header = createHeader();
        Div content = createContent(project);
        Div footer = createFooter();

        Div card = new Div(header, content, footer);
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);
        card.addClassNames(CLASS_NAME, LumoStyles.Padding.Vertical.M, LumoStyles.Padding.Horizontal.M);

        setWidth("300px");
        setHeight("200px");
        setPadding(Bottom.XL, Left.RESPONSIVE_L);
        add(card);

        createContextMenu();
        createLink();
    }

    private Div createHeader() {
        return new Div();
    }

    private Div createContent(Project project) {
        Image image = new Image(UIUtils.IMG_PATH + "avatar.png", "");
        image.setHeight("50px");
        image.setWidth("50px");

        label.setText(project.getName());
        optionsButton.getStyle().set("background", "transparent");
        optionsButton.setHeight("20px");

        HorizontalLayout contentLayout = new HorizontalLayout(image, label, optionsButton);
        contentLayout.setFlexGrow(1, label);
        contentLayout.setFlexGrow(0, optionsButton);
//        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return new Div(contentLayout);
    }

    private Div createFooter() {
        return new Div();
    }

    private void createContextMenu() {
        ContextMenu contextMenu = new ContextMenu(optionsButton);
        contextMenu.setOpenOnClick(true);

        contextMenu.addItem("Option 1", e -> {});
        contextMenu.addItem("Option 2", e -> {});
    }

    private void createLink() {
        addClickListener(e -> {
            Notification.show("Project selected..");
        });
    }



}
