package com.scruman.ui.views.sprint_retrospective;

import com.scruman.app.HasLogger;
import com.scruman.ui.MainLayout;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.layout.size.Horizontal;
import com.scruman.ui.layout.size.Uniform;
import com.scruman.ui.util.UIUtils;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.views.ViewFrame;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.*;

@PageTitle("Sprint Retrospective")
@Route(value = "retrospective", layout = MainLayout.class)
public class SprintRetrospectiveView extends ViewFrame implements HasUrlParameter<String>, BeforeEnterObserver, HasLogger {

    private Long sprintId;


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = createQuestions();
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setPadding(Uniform.RESPONSIVE_L);
        content.setWidth("80%");
        return content;
    }

    private FlexBoxLayout createQuestions() {
        return new FlexBoxLayout(createFirst(), createSecond(), createThird(), createSubmitButton());
    }

    private Component createFirst() {
        return new TextArea("What worked or went well?");
    }

    private Component createSecond() {
        return new TextArea("What caused problems, failed to work properly, or did not go well?");
    }

    private Component createThird() {
        return new TextArea("What can be done differently in the next sprint to improve the process and overcome the problems occurring previously?");
    }

    private Component createSubmitButton() {
        Button submitButton = UIUtils.createPrimaryButton("Submit");
        submitButton.addClickListener(e -> Notification.show("Sprint Retrospective submitted"));
        return submitButton;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        this.sprintId = Long.valueOf(s);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }

}
