package com.community.web.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"idx"})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void setUpdatedDateNow() {
        this.updatedDate = LocalDateTime.now();
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

}
