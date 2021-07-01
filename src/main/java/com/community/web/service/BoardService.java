package com.community.web.service;

import com.community.web.domain.Board;
import com.community.web.domain.User;
import com.community.web.domain.enums.BoardType;
import com.community.web.dto.request.BoardRequestDto;
import com.community.web.dto.response.BoardResponseDto;
import com.community.web.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public Page<BoardResponseDto> findBoardList(Pageable pageable){
        //pageable의 pageNumber가 0이하일때 0으로 초기화한 pageRequest 객체를 만들어 페이징 처리된 게시글 리스트를 반환한다. (기본 페이지 크기는 10)
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
        return boardRepository.findAllByOrderByBoardTypeDescIdxDesc(pageable).map(BoardResponseDto::new);
    }

    public BoardResponseDto findBoardByIdx(Long idx){
        return new BoardResponseDto(boardRepository.findById(idx).orElse(null));
    }

    public Board findEntityByIdx(Long idx) { return boardRepository.findById(idx).get(); }

    public BoardResponseDto save(BoardRequestDto boardRequestDto, User user){
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .boardType(BoardType.valueOf(boardRequestDto.getBoardType()))
                .user(user)
                .build();
        return new BoardResponseDto(boardRepository.save(board));
    }

    public BoardResponseDto update(Long idx,BoardRequestDto boardRequestDto){
        Board board = boardRepository.findById(idx).get();
        board = board.update(
                boardRequestDto.getTitle(),
                boardRequestDto.getContent(),
                BoardType.valueOf(boardRequestDto.getBoardType()));
        boardRepository.flush();
        return new BoardResponseDto(board);
    }
}
