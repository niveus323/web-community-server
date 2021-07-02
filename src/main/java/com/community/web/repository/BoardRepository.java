package com.community.web.repository;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
    Page<Board> findAllByOrderByBoardTypeDescIdxDesc(Pageable pageable);
    //left outer join으로 user 정보도 받을 수 있도록 만들기
    @Query(nativeQuery = true,
    value="SELECT * FROM (board inner join user on board.user_idx=user.idx) WHERE (content regexp ?1 OR title regexp ?1) ORDER BY board.idx DESC",
    countQuery = "SELECT count(*) FROM (board inner join user on board.user_idx=user.idx) ")
    Page<Board> findAllByTitleOrContentRegexOrderByIdxDesc(String regex, Pageable pageable);
}
