package com.scruman.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageUiTest extends BaseUiTest {

    public static final String VALID_USERNAME = "admin";
    public static final String VALID_PASSWORD = "admin-pass";

    @Test
    public void shouldLoginWithValidCredentials() {
        new LoginPageRobot()
                .openLoginPage()
                .capture("UX_001_Login_Page")
                .inputUsername(VALID_USERNAME)
                .inputPassword(VALID_PASSWORD)
                .clickLoginButton()
                .capture("UX_001_Logged_In_Page")
                .assertLoggedIn();
    }

    public static class LoginPageRobot {

        private static final String LOGIN_PAGE_URL = "http://localhost:1234/login";

        public LoginPageRobot openLoginPage() {
            open(LOGIN_PAGE_URL);
            return this;
        }

        public LoginPageRobot capture(String filename) {
            screenshot(filename);
            return this;
        }

        public LoginPageRobot inputUsername(String username) {
            $(By.id("username")).val(username);
            return this;
        }

        public LoginPageRobot inputPassword(String password) {
            $(By.id("password")).val(password);
            return this;
        }

        public LoginPageRobot clickLoginButton() {
            $(By.id("login-btn")).click();
            return this;
        }

        public LoginPageRobot assertLoggedIn() {
            SelenideElement welcomeMessage = $(By.id("welcome-msg"));
            assertTrue(welcomeMessage.isDisplayed());
            return this;
        }
    }
}
