package com.scruman.utils;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class BaseTest {

    @BeforeEach
    private void baseSetup() {
        MockitoAnnotations.initMocks(this);
    }
}
