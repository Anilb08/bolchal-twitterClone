package com.anil.bolchal.service;

import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Like;
import com.anil.bolchal.model.User;

import java.util.List;

public interface LikeService {

    public Like likeTwit(Long twitId, User user) throws UserException, TwitException;

    public List<Like> getAllLikes(Long twitId) throws TwitException;
}
