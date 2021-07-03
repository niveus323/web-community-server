package com.community;

import com.community.web.domain.Board;
import com.community.web.domain.Comment;
import com.community.web.domain.enums.BoardType;
import com.community.web.domain.User;
import com.community.web.domain.enums.SocialType;
import com.community.web.domain.enums.UserType;
import com.community.web.repository.BoardRepository;
import com.community.web.repository.CommentRepository;
import com.community.web.repository.UserRepository;
import com.community.web.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@EnableJpaAuditing
@SpringBootApplication
public class CommunityApplication implements WebMvcConfigurer {

    private final UserArgumentResolver userArgumentResolver;

    @Autowired
    public CommunityApplication(UserArgumentResolver userArgumentResolver){
        this.userArgumentResolver = userArgumentResolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(userArgumentResolver);
    }

    @Bean
    @Transactional
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository, CommentRepository commentRepository) throws Exception{
        return (args -> {
            User user = userRepository.save(User.builder()
                    .name("이름")
                    .password("test")
                    .email("havi@gmail.com")
                    .userType(UserType.admin)
                    .principal("textPrincipal")
                    .socialType(SocialType.GOOGLE)
                    .build());
            User user2 = userRepository.save(User.builder()
                    .name("이름2")
                    .password("test2")
                    .email("test@gmail.com")
                    .userType(UserType.commonuser)
                    .principal("textPrincipal")
                    .socialType(SocialType.GOOGLE)
                    .build());
            IntStream.rangeClosed(1,3).forEach(index -> boardRepository.save(Board.builder()
                    .title("게시글"+index)
                    .content("콘텐츠")
                    .boardType(BoardType.notice)
                    .user(user)
                    .build()));
            IntStream.rangeClosed(1,200).forEach(index -> boardRepository.save(Board.builder()
                    .title("게시글"+index)
                    .content("콘텐츠")
                    .boardType(BoardType.free)
                    .user(user2)
                    .build()));
            Board board = boardRepository.findById(1L).get();
            IntStream.rangeClosed(1,8).forEach(index -> commentRepository.save(Comment.builder()
                    .board(board)
                    .user(user2)
                    .content("댓글"+index)
                    .build()));
            IntStream.rangeClosed(9,15).forEach(index -> commentRepository.save(Comment.builder()
                    .board(board)
                    .user(user)
                    .content("댓글"+index)
                    .build()));
        });
    }


}
