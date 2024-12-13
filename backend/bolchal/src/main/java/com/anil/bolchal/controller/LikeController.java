package com.anil.bolchal.controller;

import com.anil.bolchal.dto.LikeDto;
import com.anil.bolchal.dto.mapper.LikeDtoMapper;
import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Like;
import com.anil.bolchal.model.User;
import com.anil.bolchal.service.LikeService;
import com.anil.bolchal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @PostMapping("/{twitId}/likes")
    public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(jwt);
        Like like = likeService.likeTwit(twitId,user);

        LikeDto likeDto = LikeDtoMapper.toLikeDto(like,user);
        return new ResponseEntity<LikeDto>(likeDto,HttpStatus.CREATED);
    }

    @PostMapping("/twit/{twitId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId, @RequestHeader("Authorization") String jwt) throws UserException, TwitException {

        User user = userService.findUserProfileByJwt(jwt);
       List<Like> likes = likeService.getAllLikes(twitId);

        List<LikeDto> likeDto = LikeDtoMapper.toLikeDtos(likes,user);

        return new ResponseEntity<>(likeDto,HttpStatus.CREATED);
    }
}
