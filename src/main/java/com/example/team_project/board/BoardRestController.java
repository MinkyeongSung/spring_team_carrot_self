package com.example.team_project.board;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team_project._core.utils.ApiUtils;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    // 동네생활 목록보기
    @GetMapping("/boards")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(ApiUtils.success(boardService.findAll()));
    }

}
