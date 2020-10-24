package com.scruman.ui.views.settings;


import com.scruman.backend.entity.User;
import com.scruman.backend.service.AvatarService;
import com.scruman.backend.service.UserService;
import com.scruman.ui.MainLayout;
import com.scruman.ui.components.FlexBoxLayout;
import com.scruman.ui.layout.size.Horizontal;
import com.scruman.ui.layout.size.Uniform;
import com.scruman.ui.util.css.FlexDirection;
import com.scruman.ui.views.ViewFrame;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
public class Settings extends ViewFrame {

    private UserService userService;
    private AvatarService avatarService;
    private User user;
    private String password;
    private BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

    private PasswordField oldPassword;
    private PasswordField newPassword;
    private TextField newSurnameField;
    private TextField newNameField;
    private TextField emailField;

    @Autowired
    public Settings(UserService userService, AvatarService avatarService) {
        this.avatarService = avatarService;
        this.userService = userService;

        setId("settings");
        setViewContent(createContent());
    }

    private Component createContent() {

        user = userService.getCurrentUser();
        password = user.getPassword();

        FlexBoxLayout content = new FlexBoxLayout(getAvatar(), getLineWithOldPassword(), getLineWithNewPassword(), getLineWithName(),
                getLineWithSurname(), getLineWithEmail(), getButton());

        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setPadding(Uniform.RESPONSIVE_L);
        content.setWidth("80%");

        addBinds();

        return content;
    }

    private Button getButton() {

        return new Button("Change", (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {

            if (validateOldPassword() && validateNewPassword() && binder.validate().isOk()) {

                if(newPassword.getValue().isEmpty()){
                    userService.update(user);
                }else {
                    userService.save(user);
                }
                UI.getCurrent().getPage().reload();

            }
        });

    }

    private HorizontalLayout getAvatar() {

        Label avatarLabel = new Label("Avatar");
        String pathToAvatar = avatarService.getAvatarPath();

        Image image = new Image(pathToAvatar, "Sorry - during loading image problem occurred");

        image.addClickListener(l -> new AvatarUploadDialog(userService).open());
        image.setHeight("250px");

        HorizontalLayout bothSides = createLine(new Component[]{avatarLabel}, new Component[]{image});

        return bothSides;
    }

    private HorizontalLayout getLineWithOldPassword() {

        Label oldPasswordLabel = new Label("Old password");

        oldPassword = new PasswordField();
        oldPassword.setWidth("500px");

        HorizontalLayout bothSides = createLine(new Component[]{oldPasswordLabel}, new Component[]{oldPassword});

        return bothSides;

    }

    private HorizontalLayout getLineWithNewPassword() {

        Label newPasswordLabel = new Label("New password");

        newPassword = new PasswordField();
        newPassword.setWidth("500px");

        HorizontalLayout bothSides = createLine(new Component[]{newPasswordLabel}, new Component[]{newPassword});

        return bothSides;

    }

    private HorizontalLayout getLineWithName() {

        Label newNameLabel = new Label("Name");

        newNameField = new TextField();
        newNameField.setWidth("500px");
        newNameField.setRequired(false);

        HorizontalLayout bothSides = createLine(new Component[]{newNameLabel}, new Component[]{newNameField});

        return bothSides;

    }

    private HorizontalLayout getLineWithSurname() {

        Label newSurnameLabel = new Label("Surname");

        newSurnameField = new TextField();
        newSurnameField.setWidth("500px");
        newSurnameField.setRequired(false);

        HorizontalLayout bothSides = createLine(new Component[]{newSurnameLabel}, new Component[]{newSurnameField});

        return bothSides;

    }

    private HorizontalLayout getLineWithEmail() {

        Label emailLabel = new Label("Email");

        emailField = new TextField();
        emailField.setWidth("500px");
        emailField.setRequired(false);

        HorizontalLayout bothSides = createLine(new Component[]{emailLabel}, new Component[]{emailField});

        return bothSides;

    }

    private HorizontalLayout createLine(Component[] leftComponents, Component[] rightComponents) {

        HorizontalLayout leftPartOfLine = new HorizontalLayout();
        HorizontalLayout rightPartOfLine = new HorizontalLayout();
        HorizontalLayout bothSides = new HorizontalLayout();

        leftPartOfLine.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        leftPartOfLine.setWidth("50%");
        leftPartOfLine.add(leftComponents);

        rightPartOfLine.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        rightPartOfLine.setWidth("50%");
        rightPartOfLine.add(rightComponents);

        bothSides.add(leftPartOfLine, rightPartOfLine);
        bothSides.setPadding(true);

        return bothSides;
    }

    private void addBinds() {

        binder.forField(newNameField).bind("firstName");
        binder.forField(newSurnameField).bind("lastName");
        binder.forField(emailField).bind("email");
        binder.setBean(user);
    }

    private boolean validateOldPassword() {

        if (!userService.isPasswordCorrect(oldPassword.getValue(), user.getPassword())) {
            oldPassword.setErrorMessage("old password is not correct");
            oldPassword.setInvalid(true);
        } else {
            oldPassword.setErrorMessage(null);
            return true;
        }

        return false;
    }

    private boolean validateNewPassword() {

        if (newPassword.getValue().isEmpty()) {
            return true;
        }

        if (newPassword.getValue().length() < 4) {
            newPassword.setInvalid(true);
            newPassword.setErrorMessage("password must contain at least 4 characters");
        } else if (newPassword.getValue().equals(oldPassword.getValue())) {
            newPassword.setInvalid(true);
            newPassword.setErrorMessage("new password should be different from old password");
        } else {
            user.setPassword(newPassword.getValue());
            return true;
        }

        return false;
    }
}
