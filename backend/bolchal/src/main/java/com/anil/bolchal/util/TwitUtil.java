package com.anil.bolchal.util;

import com.anil.bolchal.model.Like;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;

public class TwitUtil {

    public final static boolean isLikedByReqUser(User reqUser, Twit twit){

        for(Like like: twit.getLikes()){
            if(like.getUser().getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }

    public final static boolean isReTwitedByReqUser(User reqUser,Twit twit){

        for(User user: twit.getReTwitUser()){
            if(user.getId().equals(reqUser.getId())){
                return true;
            }
        }
        return false;
    }
}
