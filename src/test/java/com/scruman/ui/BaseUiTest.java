package com.scruman.ui;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseUiTest {

    @BeforeEach
    void setUp() {
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = true;
    }
}
