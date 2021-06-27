package com.community.web.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String context;

    @Column
    @ManyToOne
    @JoinColumn(name="BOARD_ID")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Builder
    public Comment(Long idx, String context, Board board, User user, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.idx = idx;
        this.board = board;
        this.context = context;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
