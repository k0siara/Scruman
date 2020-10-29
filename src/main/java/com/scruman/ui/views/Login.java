package com.scruman.ui.views;

import com.scruman.AppConstants;
import com.scruman.backend.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@PageTitle("Login")
@Route(AppConstants.PAGE_LOGIN)
public class Login extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

    private LoginOverlay login;

    public Login() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Scruman");

        login = new LoginOverlay();
        login.setI18n(i18n);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");
        login.setOpened(true);

        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn()) {
            // Needed manually to change the URL because of https://github.com/vaadin/flow/issues/4189
            UI.getCurrent().getPage().getHistory().replaceState(null, "");
//            event.rerouteTo(DashboardView.class);
            login.setOpened(false);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        login.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

}