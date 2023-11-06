<<<<<<< HEAD
// package com.example.team_project.user;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.List;

// import com.example.team_project._core.advice.ValidAdvice;
// import com.example.team_project._core.config.FilterConfig;
// import com.example.team_project.board.Board;
// import com.example.team_project.product.ProductService;
// import com.example.team_project.reply.Reply;

// @Import({ ValidAdvice.class, FilterConfig.class })
// @WebMvcTest
// public class UserRestControllerTest {

//     @Autowired
//     MockMvc mvc;

//     @MockBean
//     private UserService userService;

//     // 내가 쓴글, 댓글 조회
//     // 나의 당근 - 동네생활 내가 쓴글, 댓글
//     public List<UserResponse.MyWriteRespDTO> myboards(int id) {
//         // // 글쓴이의 유저아이디가 일치하는 보드들 들고오기
//         // List<Board> boardList = boardJPARepository.findbyUserId(1);

//         // // 내가 쓴글 스트림
//         // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseBoardWriteDTO =
//         // boardList.stream()
//         // .distinct()
//         // .map(b -> {
//         // UserResponse.MyWriteRespDTO.WriteBoardsDTO writeDTO = new
//         // UserResponse.MyWriteRespDTO.WriteBoardsDTO(
//         // b);
//         // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO> boardPicDTO =
//         // b.getBoardPics()
//         // .isEmpty() ? null
//         // : b.getBoardPics().stream()
//         // .limit(1)
//         // .map(bp -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO(bp))
//         // .collect(Collectors.toList());
//         // writeDTO.setBoardPics(boardPicDTO);

//         // return writeDTO;
//         // })
//         // .collect(Collectors.toList());

//         // // 1. 유저아이디가 일치하는 댓글을 찾기 (보드아이디도 알수있음.)
//         // List<Reply> replyList = replyJPARepository.findbyUserId(1);

//         // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseRDTO =
//         // replyList.stream()
//         // .map(r -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO(r.getBoard()))
//         // .collect(Collectors.toList());

//         // // 글쓴이의 유저아이디가 일치하는 보드들 들고오기
//         // List<Board> boardList = boardJPARepository.findbyUserId(1);
//         // // 유저아이디가 일치하는 댓글을 찾기 (보드아이디도 알수있음.)
//         // List<Reply> replyList = replyJPARepository.findbyUserId(1);

//         // // 내가 쓴글 스트림
//         // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseBoardWriteDTO = boardList.stream()
//         //         .distinct()
//         //         .map(b -> {
//         //             UserResponse.MyWriteRespDTO.WriteBoardsDTO writeDTO = new UserResponse.MyWriteRespDTO.WriteBoardsDTO(
//         //                     b);
//         //             List<UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO> boardPicDTO = b.getBoardPics()
//         //                     .isEmpty() ? null
//         //                             : b.getBoardPics().stream()
//         //                                     .limit(1)
//         //                                     .map(bp -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO(bp))
//         //                                     .collect(Collectors.toList());
//         //             writeDTO.setBoardPics(boardPicDTO);

//         //             return writeDTO;
//         //         })
//         //         .collect(Collectors.toList());

//         // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseRDTO = replyList.stream()
//         //         .map(r -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO(r.getBoard()))
//         //         .collect(Collectors.toList());

//         // List<UserResponse.MyWriteRespDTO> responseDTO = Stream
//         // .concat(responseBoardWriteDTO.stream(), responseRDTO.stream())
//         // .collect(Collectors.toList());

//         // List<UserResponse.MyWriteRespDTO> responseDTO = new
//         // ArrayList<>(responseBoardWriteDTO);
//         // responseDTO.addAll(responseRDTO);

//         return null;
//     }

// }
=======
package com.example.team_project.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.team_project._core.utils.ApiUtils;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.UserJoinReqDTO userJoinReqDTO, Errors errors) {
        UserResponse.UserJoinRespDTO responseDTO = userService.join(userJoinReqDTO);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.UserLoginReqDTO userLoginReqDTO, Errors errors) {

        UserResponse.UserLoginRespDTO responseDTO = userService.login(userLoginReqDTO);
        return ResponseEntity.ok().header("Authorization", responseDTO.getJwt())
                .body(ApiUtils.success(responseDTO.getUser()));

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 회원정보 수정
    @PostMapping("/user/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid UserRequest.UserUpdateReqDTO userUpdateReqDTO,
            @PathVariable Integer id, Error error) {
        UserResponse.UserUpdateRespDTO responseDTO = userService.update(userUpdateReqDTO, id);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
}
>>>>>>> 37e61ae4c1e7418858a3c1b8fb94f6a5ccd22b55
