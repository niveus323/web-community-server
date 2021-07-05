package com.community.web.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode(of = {"idx"})
@MappedSuperclass
public abstract class BaseEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    public void setUpdatedDateNow() {
        this.updatedDate = LocalDateTime.now();
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

}
