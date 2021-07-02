package com.community.web.domain;

import com.community.web.domain.enums.SocialType;
import com.community.web.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true, of={"email"})
@NoArgsConstructor
@Entity
@Table
@Getter
public class User extends BaseEntity implements Serializable {
    @Column
    private String name;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    private String principal;

    @Column
    private SocialType socialType;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public User(String name, String password, String email, String principal, SocialType socialType, UserType userType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
        this.userType = userType;
    }
}
