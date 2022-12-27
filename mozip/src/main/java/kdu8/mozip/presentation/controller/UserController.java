package kdu8.mozip.presentation.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kdu8.mozip.entity.User;
import kdu8.mozip.exception.UserDoesntExistException;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import kdu8.mozip.presentation.dto.user.UserResponse;
import kdu8.mozip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins = "127.0.0.1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("uers/me")
    @ApiOperation(value = "마이 페이지", notes = "요청을 날린 유저의 정보 및 유저가 쓴 글, 신청한 글을 응답함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "로그인 되지 않음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserResponse> getMyData(HttpServletRequest request) {

        try {
            User user = userService.authUser(request);
            List<BoardListResponse> myBoards = userService.getMyBoards(user.getId());
            List<BoardListResponse> myApplyBoards = userService.getMyApplyBoards(user.getId());

            return ResponseEntity.status(HttpStatus.OK).body(UserResponse.builder()
                    .user(user)
                    .myBoards(myBoards)
                    .myApplyBoards(myApplyBoards)
                    .build());
        } catch (UserDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }


    }

}
