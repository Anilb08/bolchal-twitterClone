package com.anil.bolchal.controller;

import com.anil.bolchal.dto.TwitDto;
import com.anil.bolchal.dto.mapper.TwitDtoMapper;
import com.anil.bolchal.exception.TwitException;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;
import com.anil.bolchal.request.TwitReplyRequest;
import com.anil.bolchal.response.ApiResponse;
import com.anil.bolchal.service.TwitServices;
import com.anil.bolchal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twits")
public class TwitController {

    @Autowired
    private TwitServices twitServices;

    @Autowired
    private UserService userService;


    // Create Twit api

    @PostMapping("/create")
    public ResponseEntity<TwitDto>createTwit(@RequestBody Twit req, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitServices.createTwit(req,user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TwitDto>replyTwit(@RequestBody TwitReplyRequest req, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitServices.createReply(req,user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }



    @PutMapping("/{twitId}/retwit")
    public ResponseEntity<TwitDto>reTwit(@PathVariable Long twitId, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitServices.reTwit(twitId,user);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }


    @GetMapping("/{twitId}")
    public ResponseEntity<TwitDto>findTwitById(@PathVariable Long twitId, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        Twit twit = twitServices.findById(twitId);

        TwitDto twitDto = TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @DeleteMapping("/{twitId}")
    public ResponseEntity<ApiResponse>deleteTwit(@PathVariable Long twitId, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);
        twitServices.deleteTwitById(twitId,user.getId());
      ApiResponse res = new ApiResponse();
      res.setMessage("Twit is deleted successfully");
      res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<TwitDto>>getAllTwits( @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twits = twitServices.findAllTwit();

        List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TwitDto>>getUsersAllTwits(@PathVariable Long userId, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twits = twitServices.getUserTwit(user);

        List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TwitDto>>findTwitsByLikesContainsUser(@PathVariable Long userId, @RequestHeader("Authorization")String jwt) throws UserException, TwitException{

        User user = userService.findUserProfileByJwt(jwt);

        List<Twit> twits = twitServices.findByLikesContainsUser(user);

        List<TwitDto> twitDto = TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }
}
