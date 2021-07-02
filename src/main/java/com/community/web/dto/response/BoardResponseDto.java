package com.community.web.dto.response;

import com.community.web.domain.Board;
import com.community.web.domain.projection.BoardWithUser;
import com.community.web.dto.UserDto;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BoardResponseDto {
    private final Long idx;
    private final String title;
    private final String content;
    private final String boardType;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
    private final UserDto user;

    public BoardResponseDto(Board board){
        this.idx = board.getIdx();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.boardType = board.getBoardType().getValue();
        this.createdDate = board.getCreatedDate();
        this.updatedDate = board.getUpdatedDate();
        if(board.getUser()!=null)   this.user = new UserDto(board.getUser());
        else this.user = null;
    }

    public BoardResponseDto(BoardWithUser boardWithUser) {
        this.idx = boardWithUser.getIdx();
        this.title = boardWithUser.getTitle();
        this.content = boardWithUser.getContent();
        this.boardType = boardWithUser.getBoard_Type().getValue();
        this.createdDate = boardWithUser.getCreated_Date();
        this.updatedDate = boardWithUser.getUpdated_Date();
        this.user = new UserDto(boardWithUser.getUserIdx(), boardWithUser.getUserName(), boardWithUser.getUserEmail(), boardWithUser.getUserType().getValue());
    }
}
