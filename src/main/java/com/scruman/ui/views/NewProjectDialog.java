package com.scruman.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;

public class NewProjectDialog extends Dialog {

    public NewProjectDialog() {
        Button button = new Button("test");
        add(button);
    }
}
