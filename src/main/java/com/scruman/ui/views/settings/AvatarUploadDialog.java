package com.scruman.ui.views.settings;

import com.scruman.backend.entity.User;
import com.scruman.backend.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AvatarUploadDialog extends Dialog {

    private UserService userService;
    private User currentUser;

    public AvatarUploadDialog(UserService userService){
        this.userService = userService;
        this.currentUser = userService.getCurrentUser();

        add(createContent());
    }

    public Component createContent(){

        MemoryBuffer fileBuffer = new MemoryBuffer();
        File targetFile = new File("src/main/webapp/images/avatar_" + currentUser.getId() + ".png");
        Upload upload = new Upload(fileBuffer);

        upload.addFinishedListener(e -> {
            InputStream inputStream = fileBuffer.getInputStream();

            try {
                FileUtils.copyInputStreamToFile(inputStream, targetFile);

                UI.getCurrent().getPage().reload();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

        });

        return upload;

    }
}