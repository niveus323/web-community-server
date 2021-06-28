package com.community.web.repository;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
    Page<Board> findAllByUser(User user, Pageable pageable);
}
