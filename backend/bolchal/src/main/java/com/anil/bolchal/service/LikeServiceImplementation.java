package com.anil.bolchal.service;

import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Like;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;
import com.anil.bolchal.repository.LikeRepository;
import com.anil.bolchal.repository.TwitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImplementation implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TwitServices twitServices;

    @Autowired
    private TwitRepository twitRepository;

    @Override
    public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
        Like isLikeExist=likeRepository.isLikeExist(user.getId(),twitId);

        if(isLikeExist != null){
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }

        Twit twit = twitServices.findById(twitId);

        Like like = new Like();

        like.setTwit(twit);

        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        twit.getLikes().add(savedLike);
        twitRepository.save(twit);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long twitId) throws TwitException {

        Twit twit = twitServices.findById(twitId);

        List<Like>likes = likeRepository.findByTwitId(twitId);

        return likes;
    }
}
