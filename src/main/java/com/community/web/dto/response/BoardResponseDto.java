package com.community.web.dto.response;

import com.community.web.domain.Board;
import com.community.web.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BoardResponseDto {
    private Long idx;
    private String title;
    private String subTitle;
    private String content;
    private String boardType;
    private LocalDateTime updatedDate;
    private UserDto user;

    public BoardResponseDto(Board board){
        this.idx = board.getIdx();
        this.title = board.getTitle();
        this.subTitle = board.getSubTitle();
        this.content = board.getContent();
        this.boardType = board.getBoardType().getValue();
        this.updatedDate = board.getUpdatedDate();
        if(board.getUser()!=null)   this.user = new UserDto(board.getUser());
        else this.user = null;
    }
}
