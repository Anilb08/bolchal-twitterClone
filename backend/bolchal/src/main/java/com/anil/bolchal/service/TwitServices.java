package com.anil.bolchal.service;

import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;
import com.anil.bolchal.request.TwitReplyRequest;

import java.util.List;

public interface TwitServices {

    public Twit createTwit(Twit req, User user) throws UserException;
    public List<Twit> findAllTwit();
    public Twit reTwit(Long twitId,User user) throws UserException, TwitException;
    public Twit findById(Long twitId) throws TwitException;

    public void deleteTwitById(Long twitId, Long userId) throws TwitException,UserException;

    public Twit removeFromReTwit(Long twitId,User user) throws TwitException,UserException;

    public Twit createReply(TwitReplyRequest req, User user) throws TwitException;

    public List<Twit> getUserTwit(User user);

    public List<Twit>findByLikesContainsUser(User user);

}
