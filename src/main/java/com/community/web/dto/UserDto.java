package com.community.web.dto;

import com.community.web.domain.User;
import com.community.web.domain.enums.SocialType;
import com.community.web.domain.projection.BoardWithUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@EqualsAndHashCode(of = "idx")
public class UserDto implements Serializable {
    private final Long idx;
    private final String name;
    private final String email;
    private final String userType;

    public UserDto(User user){
        this.idx = user.getIdx();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType().getValue();
    }

    public UserDto(Long idx, String name, String email, String userType) {
        this.idx = idx;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }
}
