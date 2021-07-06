package com.community.web.domain.projection;

import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.domain.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardWithUser {
    Long getIdx();
    LocalDateTime getCreated_Date();
    LocalDateTime getUpdated_Date();
    BoardType getBoard_Type();
    String getContent();
    String getTitle();
    Long getUserIdx();
    String getUserEmail();
    String getUserName();
    UserType getUserType();
    Long getView();
    List<User> getVotedBy();
}
