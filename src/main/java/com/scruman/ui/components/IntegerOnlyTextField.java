package com.scruman.ui.components;


import com.vaadin.flow.component.textfield.TextField;

public class IntegerOnlyTextField extends TextField {

    public IntegerOnlyTextField() {
        addValueChangeListener(event -> {
            String text = event.getValue();
            try {
                Integer.valueOf(text);
            } catch (NumberFormatException e) {
                setValue(event.getOldValue());
            }
        });
    }
}
