package com.scruman.backend.service;

import com.scruman.backend.beans.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
public class AvatarService {

    private CurrentUser currentUser;

    public String getAvatarPath(){
        return hasUserOwnAvatar() ? getPathToUserAvatar() : getPathToAnonymousAvatar();
    }

    public boolean hasUserOwnAvatar() {
        File fileWithAvatar = new File("src/main/webapp/images/avatar_" + currentUser.get().getId() + ".png");
        return fileWithAvatar.exists();
    }

    public String getPathToUserAvatar(){
        return "images/avatar_" + currentUser.get().getId() + ".png";
    }

    public String getPathToAnonymousAvatar(){
        return "images/avatar_anonymous.png";
    }

}
