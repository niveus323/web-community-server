package com.community.web.repository;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
    @Query(nativeQuery = true,
            value="SELECT * FROM (board inner join user on board.user_idx=user.idx) ORDER BY board.idx DESC",
            countQuery = "SELECT count(*) FROM (board inner join user on board.user_idx=user.idx)")
    Page<Board> findAllByOrderByBoardTypeDescIdxDesc(Pageable pageable);
    @Query(nativeQuery = true,
    value="SELECT * FROM (board inner join user on board.user_idx=user.idx) WHERE (content regexp :regex OR title regexp :regex) ORDER BY board.idx DESC",
    countQuery = "SELECT count(*) FROM (board inner join user on board.user_idx=user.idx) WHERE (content regexp :regex OR title regexp :regex)")
    Page<Board> findAllByTitleOrContentRegexOrderByIdxDesc(@Param("regex") String regex, Pageable pageable);
}
