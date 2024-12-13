package com.anil.bolchal.dto.mapper;

import com.anil.bolchal.dto.TwitDto;
import com.anil.bolchal.dto.UserDto;
import com.anil.bolchal.model.Twit;
import com.anil.bolchal.model.User;
import com.anil.bolchal.util.TwitUtil;

import java.util.ArrayList;
import java.util.List;

public class TwitDtoMapper {

    public static TwitDto toTwitDto(Twit twit, User reqUser){
        UserDto user = UserDtoMapper.toUserDto(twit.getUser());

        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser,twit);
        boolean isReTwited =TwitUtil.isReTwitedByReqUser(reqUser,twit);

        List<Long> reTwitUserId = new ArrayList<>();

        for(User user1: twit.getReTwitUser()){
            reTwitUserId.add(user1.getId());
        }

        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTwits().size());
        twitDto.setTotalReTweets(twit.getReTwitUser().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setReTwit(isReTwited);
        twitDto.setReTwitUsersId(reTwitUserId);
        twitDto.setReplyTwits(toTwitDtos(twit.getReplyTwits(),reqUser));
        twitDto.setVideo(twit.getVideo());



        return twitDto;
    }

    public static List<TwitDto> toTwitDtos(List<Twit>twits,User reqUser){
        List<TwitDto>twitDtos = new ArrayList<>();
        for(Twit twit:twits){
            TwitDto twitDto = toReplyTwitDto(twit,reqUser);
            twitDtos.add(twitDto);
        }
        return twitDtos;
    }

    private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {

        UserDto user = UserDtoMapper.toUserDto(twit.getUser());

        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser,twit);
        boolean isReTwited =TwitUtil.isReTwitedByReqUser(reqUser,twit);

        List<Long> reTwitUserId = new ArrayList<>();

        for(User user1: twit.getReTwitUser()){
            reTwitUserId.add(user1.getId());
        }

        TwitDto twitDto = new TwitDto();
        twitDto.setId(twit.getId());
        twitDto.setContent(twit.getContent());
        twitDto.setCreatedAt(twit.getCreatedAt());
        twitDto.setImage(twit.getImage());
        twitDto.setTotalLikes(twit.getLikes().size());
        twitDto.setTotalReplies(twit.getReplyTwits().size());
        twitDto.setTotalReTweets(twit.getReTwitUser().size());
        twitDto.setUser(user);
        twitDto.setLiked(isLiked);
        twitDto.setReTwit(isReTwited);
        twitDto.setReTwitUsersId(reTwitUserId);
        twitDto.setVideo(twit.getVideo());



        return twitDto;
    }
}
