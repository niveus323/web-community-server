package com.community.web.domain;

import com.community.web.domain.enums.SocialType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true, of={"email"})
@NoArgsConstructor
@Entity
@Table
public class User extends BaseEntity implements Serializable {
    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String principal;

    @Column
    private SocialType socialType;

    @Builder
    public User(String name, String password, String email, String principal, SocialType socialType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
    }
}
