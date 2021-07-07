package com.community.web.domain;

import com.community.web.domain.enums.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table
public class Board extends BaseEntity{
    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Long view;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="votes", joinColumns = @JoinColumn(name="board_idx"), inverseJoinColumns = @JoinColumn(name="user_idx"))
    Set<User> votedBy;

    @Builder
    public Board(String title, String content, BoardType boardType, User user) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
        this.view = 0L;
        this.votedBy = new HashSet<>();
        this.setCreatedDateNow();
        this.setUpdatedDateNow();
    }

    public Board updateView(){
        this.view++;
        return this;
    }
}
