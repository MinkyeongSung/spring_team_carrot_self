package com.example.team_project.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;

    // 동네생활 목록보기
    public List<BoardResponse.BoardListRespDTO> findAll() {
        List<Board> dtos = boardJPARepository.findAll();
        List<BoardResponse.BoardListRespDTO> responseDTO = dtos.stream()
                .map(b -> new BoardResponse.BoardListRespDTO(b))
                .collect(Collectors.toList());
        return responseDTO;
    }

}
