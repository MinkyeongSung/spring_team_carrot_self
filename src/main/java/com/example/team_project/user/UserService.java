package com.example.team_project.user;

import com.example.team_project._core.utils.JwtTokenUtils;
import com.example.team_project.board.Board;
import com.example.team_project.board.BoardJPARepository;
import com.example.team_project.reply.Reply;
import com.example.team_project.reply.ReplyJPARepository;
import com.example.team_project.user.UserResponse.UserUpdateRespDTO;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_project._core.erroes.exception.Exception400;
import com.example.team_project._core.erroes.exception.Exception404;

import javax.persistence.EntityManager;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final EntityManager em;
    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;

    // 회원가입
    @Transactional
    public UserResponse.UserJoinRespDTO join(UserRequest.UserJoinReqDTO userJoinReqDTO) {

        User user;
        try {
            user = userJPARepository.save(userJoinReqDTO.toEntity());
        } catch (Exception e) {
            throw new Exception400("존재하는 이메일입니다.");
        }
        return new UserResponse.UserJoinRespDTO(user);
    }

    // 로그인
    public UserResponse.UserLoginRespDTO login(UserRequest.UserLoginReqDTO userLoginReqDTO) {

        User user = userJPARepository.findByUsername(userLoginReqDTO.getUsername());
        if (user == null) {
            throw new Exception400("유저네임이 없습니다.");
        }

        if (!user.getPassword().equals(userLoginReqDTO.getPassword())) {
            throw new Exception400("패스워드가 잘못되었습니다.");
        }

        String jwt = JwtTokenUtils.create(user);

        return new UserResponse.UserLoginRespDTO(jwt, user);
    }

    // 회원정보수정
    @Transactional
    public UserResponse.UserUpdateRespDTO update(UserRequest.UserUpdateReqDTO userUpdateReqDTO, Integer userId) {
        User user = userJPARepository.findByUsername(userUpdateReqDTO.getUsername()); // 영속화
        if (user.getUsername() == null) {
            throw new Exception404("사용자를 찾을 수 없습니다.");
        }
        userJPARepository.mUpdateUser(userId, userUpdateReqDTO.getUsername(),
                userUpdateReqDTO.getPassword(), userUpdateReqDTO.getNickname());

        System.out.println("user 값은? " + user.getEmail());
        System.out.println("user 값은? " + user.getUsername());
        System.out.println("user 값은? " + user.getNickname());

        // 변경 내용을 데이터베이스에 반영
        userJPARepository.flush();

        // 강제초기화하기
        user = userJPARepository.findById(userId).orElseThrow(() -> new Exception400("유저정보가 없습니다."));
        em.refresh(user);

        System.out.println();
        return new UserResponse.UserUpdateRespDTO(user);

    }

    // 나의 당근 - 동네생활 내가 쓴글, 댓글
    public UserResponse.MyWriteRespDTO myboards(int id) {
        // // 글쓴이의 유저아이디가 일치하는 보드들 들고오기
        // List<Board> boardList = boardJPARepository.findbyUserId(1);

        // // 내가 쓴글 스트림
        // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseBoardWriteDTO =
        // boardList.stream()
        // .distinct()
        // .map(b -> {
        // UserResponse.MyWriteRespDTO.WriteBoardsDTO writeDTO = new
        // UserResponse.MyWriteRespDTO.WriteBoardsDTO(
        // b);
        // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO> boardPicDTO =
        // b.getBoardPics()
        // .isEmpty() ? null
        // : b.getBoardPics().stream()
        // .limit(1)
        // .map(bp -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO(bp))
        // .collect(Collectors.toList());
        // writeDTO.setBoardPics(boardPicDTO);

        // return writeDTO;
        // })
        // .collect(Collectors.toList());

        // // 1. 유저아이디가 일치하는 댓글을 찾기 (보드아이디도 알수있음.)
        // List<Reply> replyList = replyJPARepository.findbyUserId(1);

        // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> responseRDTO =
        // replyList.stream()
        // .map(r -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO(r.getBoard()))
        // .collect(Collectors.toList());

        // 글쓴이의 유저아이디가 일치하는 보드들 들고오기
        List<Board> boardList = boardJPARepository.findbyUserId(1);

        // 내가 쓴글 스트림
        List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> board = boardList.stream()
                .distinct()
                .map(b -> {
                    UserResponse.MyWriteRespDTO.WriteBoardsDTO writeDTO = new UserResponse.MyWriteRespDTO.WriteBoardsDTO(
                            b);
                    List<UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO> boardPicDTO = b.getBoardPics()
                            .isEmpty() ? null
                                    : b.getBoardPics().stream()
                                            .limit(1)
                                            .map(bp -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO.BoardPicDTO(bp))
                                            .collect(Collectors.toList());
                    writeDTO.setBoardPics(boardPicDTO);

                    return writeDTO;
                })
                .collect(Collectors.toList());

        // 유저아이디가 일치하는 댓글을 찾기 (보드아이디도 알수있음.)
        List<Reply> replyList = replyJPARepository.findbyUserId(1);

        List<Board> boardList1 = new ArrayList<>();

        for (Reply reply : replyList) {
            Integer boardId = reply.getBoard().getId();
            Board board2 = boardJPARepository.findById(boardId).orElse(null); // 또는 findById 메서드를 사용하여 Board를 가져옵니다.
            if (board2 != null) {
                boardList1.add(board2);
            }
        }

        // // 이걸 바탕으로 List<Board>형식으로 담기.
        // List<Board> reply = replyList.stream()
        // .map(rr -> New Board(r.getBoard().g))

        // //??
        // List<UserResponse.MyWriteRespDTO.WriteBoardsDTO> reply = replyList.stream()
        // .map(r -> new UserResponse.MyWriteRespDTO.WriteBoardsDTO(r.getBoard()))
        // .collect(Collectors.toList());

        // List<UserResponse.MyWriteRespDTO> responseDTO = Stream
        // .concat(responseBoardWriteDTO.stream(), responseRDTO.stream())
        // .collect(Collectors.toList());

        // List<UserResponse.MyWriteRespDTO> responseDTO = new
        // ArrayList<>(responseBoardWriteDTO);
        // responseDTO.addAll(responseRDTO);

        // return new UserResponse.MyWriteRespDTO(board, reply);

        // UserResponse.MyWriteRespDTO 객체 생성

        return new UserResponse.MyWriteRespDTO(boardList, boardList1);
    }
}
