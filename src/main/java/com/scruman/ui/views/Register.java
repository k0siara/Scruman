package com.scruman.ui.views;

import com.scruman.AppConstants;
import com.scruman.backend.entity.User;
import com.scruman.backend.security.SecurityUtils;
import com.scruman.backend.service.UserService;
import com.scruman.ui.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Register")
@Route(value = AppConstants.PAGE_REGISTER, layout = MainLayout.class)
public class Register extends VerticalLayout implements BeforeEnterObserver {

    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField usernameField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout buttonBar = new HorizontalLayout();

    private UserService userService;
    private BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
    private User user;

    @Autowired
    public Register(UserService userService) {
        this.userService = userService;
        user = new User();

        setLayout();
        setFormItems();
        addFormItems();
        addButtons();
        addBinds();
    }

    private void setLayout() {
        buttonBar.setMargin(true);
        formLayout.setMaxWidth("480px");
        add(formLayout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonBar);
    }

    private void setFormItems() {
        firstNameField.setValueChangeMode(ValueChangeMode.EAGER);
        lastNameField.setValueChangeMode(ValueChangeMode.EAGER);
        usernameField.setValueChangeMode(ValueChangeMode.EAGER);
        emailField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
    }

    private void addFormItems() {
        formLayout.addFormItem(firstNameField, "First name");
        formLayout.addFormItem(lastNameField, "Last name");
        formLayout.addFormItem(usernameField, "Username");
        formLayout.addFormItem(emailField, "E-Mail");
        formLayout.addFormItem(passwordField, "Password");
        formLayout.add(buttonBar);
    }

    private void addButtons() {
        Button resetButton = new Button("Reset", (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            binder.readBean(null);
        });
        Button registerButton = new Button("Register", (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if(binder.validate().isOk()) {
                userService.save(user);
                UI.getCurrent().navigate(AppConstants.PAGE_LOGIN);
            }
        });

        resetButton.setWidth("90px");
        registerButton.setWidth("90px");

        buttonBar.add(resetButton);
        buttonBar.add(registerButton);
    }

    private void addBinds() {
        binder.forField(firstNameField)
                .withValidator(name -> name.length() >= 3, "First name must contain at least 4 characters")
                .bind(User::getFirstName, User::setFirstName);
        binder.forField(lastNameField)
                .withValidator(name -> name.length() >= 3, "Last name must contain at least 4 characters")
                .bind(User::getLastName, User::setLastName);
        binder.forField(emailField)
                .withValidator(new EmailValidator("Not a valid e-mail"))
                .withValidator(email -> !userService.existsByEmail(email), "There's already an account with that e-mail")
                .bind(User::getEmail, User::setEmail);
        binder.forField(passwordField)
                .withValidator(pass -> pass.length() >= 4, "Password must contain at least 4 characters")
                .bind(User::getPassword, User::setPassword);
        binder.forField(usernameField)
                .withValidator(name -> name.length() >= 3, "Username must contain at least 4 characters")
                .withValidator(username -> !userService.existsByUsername(username), "There's already an account with that username")
                .bind(User::getUsername, User::setUsername);
        binder.setBean(user);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(SecurityUtils.isUserLoggedIn()) {
            event.forwardTo("");
        }
    }
}