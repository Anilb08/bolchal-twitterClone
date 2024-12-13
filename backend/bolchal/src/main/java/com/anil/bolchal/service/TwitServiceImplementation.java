package com.anil.bolchal.service;

import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;
import com.anil.bolchal.repository.TwitRepository;
import com.anil.bolchal.request.TwitReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TwitServiceImplementation implements TwitServices {

    @Autowired
    private TwitRepository twitRepository;

    @Override
    public Twit createTwit(Twit req, User user) throws UserException {
        Twit twit = new Twit();
        twit.setContent(req.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(req.getImage());
        twit.setUser(user);
        twit.setReply(false);
        twit.setTwit(true);
        twit.setVideo(req.getVideo());

        return twitRepository.save(twit);
    }

    @Override
    public List<Twit> findAllTwit() {

        return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
    }

    @Override
    public Twit reTwit(Long twitId, User user) throws UserException, TwitException {
        Twit twit = findById(twitId);
        if(twit.getReTwitUser().contains(user)){
            twit.getReTwitUser().remove(user);
        }
        else{
            twit.getReTwitUser().add(user);
        }
        return twitRepository.save(twit);
    }

    @Override
    public Twit findById(Long twitId) throws TwitException {
        Twit twit = twitRepository.findById(twitId)
                .orElseThrow(() -> new TwitException("Twit not found with id "+twitId));


        return twit;
    }

    @Override
    public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
          Twit twit = findById(twitId);
          if(!userId.equals(twit.getUser().getId())){
              throw new UserException("you can't delete another users twit");

          }
          twitRepository.deleteById(twit.getId());
    }

    @Override
    public Twit removeFromReTwit(Long twitId, User user) throws TwitException, UserException {
        return null;
    }

    @Override
    public Twit createReply(TwitReplyRequest req, User user) throws TwitException {

        Twit replyFor = findById(req.getTwitId());

        Twit twit = new Twit();
        twit.setContent(req.getContent());
        twit.setCreatedAt(LocalDateTime.now());
        twit.setImage(req.getImage());
        twit.setUser(user);
        twit.setReply(true);
        twit.setTwit(false);
        twit.setReplyFor(replyFor);


        Twit savedReply = twitRepository.save(twit);
       // twit.getReplyTwits().add(savedReply);
        replyFor.getReplyTwits().add(savedReply);
        twitRepository.save(replyFor);
        return replyFor;
    }

    @Override
    public List<Twit> getUserTwit(User user) {

        return twitRepository.findByReTwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user,user.getId());
    }

    @Override
    public List<Twit> findByLikesContainsUser(User user) {

        return twitRepository.findByLikesUser_id(user.getId());
    }
}
