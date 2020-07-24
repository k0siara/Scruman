package com.scruman.ui.views.projects;

import com.scruman.backend.entity.Project;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.layout.size.Bottom;
import com.scruman.ui.layout.size.Left;
import com.scruman.ui.util.LumoStyles;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.BorderRadius;
import com.scruman.ui.util.css.Shadow;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NewProjectCard extends FlexBoxLayout {

    private static final String CLASS_NAME = "project_card";

    private Button plusButton = new Button(VaadinIcon.PLUS.create());

    public NewProjectCard(ComponentEventListener<ClickEvent<FlexLayout>> clickListener) {
        Div header = createHeader();
        Div content = createContent();
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

        addClickListener(clickListener);
    }

    private Div createHeader() {
        return new Div();
    }

    private Div createContent() {
        HorizontalLayout contentLayout = new HorizontalLayout(plusButton);
        contentLayout.setFlexGrow(1, plusButton);
//        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return new Div(contentLayout);
    }

    private Div createFooter() {
        return new Div();
    }
}
