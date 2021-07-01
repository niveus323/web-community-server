package com.community.web.dto;

import com.community.web.domain.User;
import com.community.web.domain.enums.SocialType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@EqualsAndHashCode(of = "idx")
public class UserDto implements Serializable {
    private Long idx;
    private String name;
    private String email;
    private String userType;

    public UserDto(User user){
        this.idx = user.getIdx();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType().getValue();
    }
}
