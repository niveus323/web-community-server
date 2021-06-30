package com.community.web.repository;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByBoardIdxOrderByIdx(Long board_id, Pageable pageable);
    Page<Comment> findAllByBoardIdxAndIdxLessThanOrderByIdxDesc(Long board_id, Long idx, Pageable pageable);
}
