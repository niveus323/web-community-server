package com.community.web.repository;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.domain.projection.BoardOnlyContainTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = BoardOnlyContainTitle.class)
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);

    @EntityGraph(attributePaths = {"user", "votedBy"})
    Optional<Board> findById(Long aLong);

    @Query(nativeQuery = true,
    value = "SELECT b.*, u.idx as userIdx, u.name as userName, u.email as userEmail, u.user_type as userType, v.user_idx as votedBy " +
            "FROM board b inner join user u on b.user_idx = u.idx left outer join votes v on b.idx = v.board_idx " +
            "ORDER BY b.board_type desc, b.idx DESC",
            countQuery = "SELECT count(*) FROM board")
    <T> Page<T> findAllByOrderByBoardTypeDescIdxDesc(Pageable pageable, Class<T> type);

    @Query(nativeQuery = true,
        value="SELECT b.*, u.idx as userIdx, u.name as userName, u.email as userEmail, u.user_type as userType, v.user_idx as votedBy " +
                "FROM board b inner join user u on b.user_idx = u.idx left outer join votes v on b.idx = v.board_idx " +
                "WHERE (content regexp :regex OR title regexp :regex) ORDER BY b.idx DESC",
        countQuery = "SELECT count(*) FROM board WHERE (content regexp :regex OR title regexp :regex)")
    <T> Page<T> findAllByTitleOrContentRegexOrderByIdxDesc(@Param("regex") String regex, Pageable pageable, Class<T> type);

    @Modifying
    @Query("update Board b set b.title = :title, b.content = :content, b.boardType = :boardType, b.updatedDate = current_timestamp where b.idx = :id")
    int updateBoardById(@Param("id") Long id,@Param("title") String title,@Param("content") String content,@Param("boardType") BoardType boardType);

    @Modifying
    @Query(nativeQuery = true,
    value = "insert into votes values (:user_id, :board_id)")
    int voteBoardById(@Param("user_id") Long userId, @Param("board_id") Long boardId);
}
