package com.anil.bolchal.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TwitDto {

    private Long id;

    private String content;

    private String image;

    private String video;

    private UserDto user;

    private LocalDateTime createdAt;

    private int totalLikes;

    private int totalReplies;

    private int totalReTweets;

    private boolean isLiked;

    private boolean isReTwit;


    private List<Long> reTwitUsersId;

    private List<TwitDto> replyTwits;


}
