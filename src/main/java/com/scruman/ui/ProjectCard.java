package com.scruman.ui;

import com.scruman.ui.util.LumoStyles;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BorderRadius;
import com.scruman.ui.util.css.Shadow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ProjectCard extends Div {

    private static final String CLASS_NAME = "project_card";

    private Div header;
    private Div content;
    private Div footer;

    public ProjectCard() {
        header = createHeader();
        content = createContent();
        footer = createFooter();

        add(header, content, footer);

        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, this);
        UIUtils.setBorderRadius(BorderRadius.S, this);
        UIUtils.setShadow(Shadow.XS, this);
        addClassNames(CLASS_NAME, LumoStyles.Padding.Vertical.M, LumoStyles.Padding.Horizontal.M);

        setWidth("300px");
        setHeight("200px");
    }

    private Div createHeader() {
        return new Div();
    }

    private Div createContent() {
        Image image = new Image(UIUtils.IMG_PATH + "avatar.png", "");
        image.setHeight("50px");
        image.setWidth("50px");

        Label label = new Label("Projekt testowy");

        Button optionsButton = new Button(VaadinIcon.ELLIPSIS_DOTS_H.create());
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



}
