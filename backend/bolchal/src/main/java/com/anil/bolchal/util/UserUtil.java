package com.anil.bolchal.util;

import com.anil.bolchal.model.User;

public class UserUtil {

    public static final boolean isReqUser(User reqUser, User user2){
        return reqUser.getId().equals(user2.getId());
    }

    public static final boolean isFollowedbyReqUser(User reqUser,User user2){
        return reqUser.getFollowings().contains(user2);
    }
}
