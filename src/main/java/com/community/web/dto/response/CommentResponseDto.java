package com.community.web.dto.response;

import com.community.web.domain.Comment;
import com.community.web.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class CommentResponseDto {
    private final Long idx;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final UserDto user;

    public CommentResponseDto(Comment comment){
        this.idx = comment.getIdx();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.updatedDate = comment.getUpdatedDate();
        this.user = new UserDto(comment.getUser());
    }
}
