package com.scruman.backend.service;

import com.scruman.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AvatarService {

    private UserService userService;
    private User currentUser;

    @Autowired
    public AvatarService(UserService userService) {
        this.userService = userService;
    }

    public String getAvatarPath(){
        return hasUserOwnAvatar() ? getPathToUserAvatar() : getPathToAnonymousAvatar();
    }

    public boolean hasUserOwnAvatar() {
        currentUser = userService.getCurrentUser();

        File fileWithAvatar = new File("src/main/webapp/images/avatar_" + currentUser.getId() + ".png");

        return fileWithAvatar.exists();
    }

    public String getPathToUserAvatar(){
        return "images/avatar_" + currentUser.getId() + ".png";
    }

    public String getPathToAnonymousAvatar(){
        return "images/avatar_anonymous.png";
    }

}
