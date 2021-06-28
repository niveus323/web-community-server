package com.community;

import com.community.web.domain.Board;
import com.community.web.domain.enums.BoardType;
import com.community.web.domain.User;
import com.community.web.repository.BoardRepository;
import com.community.web.repository.UserRepository;
import com.community.web.resolver.UserArgumentResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

@SpringBootTest
public class JpaMappingTest {
    private final String boardTestTitle = "테스트";
    private final String email = "test@gmai.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void init(){
        User user = userRepository.save(User.builder()
                .name("이름")
                .password("test")
                .email(email).build());
        boardRepository.save(Board.builder()
                .title(boardTestTitle)
                .subTitle("서브 타이틀")
                .content("콘텐츠")
                .boardType(BoardType.free)
                .user(user).build());
    }

    @Test
    public void shouldHaveOneBoard(){
        User user = userRepository.findByEmail(email);
        Assertions.assertEquals(user.getName(),"이름");
        Assertions.assertEquals(user.getEmail(),email);
        Assertions.assertEquals(user.getCreatedDate(), LocalDateTime.now());

        Board board = boardRepository.findByUser(user);
        Assertions.assertEquals(board.getTitle(),boardTestTitle);
        Assertions.assertEquals(board.getSubTitle(),"서브 타이틀");
        Assertions.assertEquals(board.getContent(),"콘텐츠");
        Assertions.assertEquals(board.getBoardType(),BoardType.free);
        Assertions.assertEquals(board.getUpdatedDate(), LocalDateTime.now());
    }
}
