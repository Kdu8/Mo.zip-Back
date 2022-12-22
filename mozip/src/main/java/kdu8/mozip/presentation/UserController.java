package kdu8.mozip.presentation;

import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.BoardListResponse;
import kdu8.mozip.presentation.dto.UserResponse;
import kdu8.mozip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("uers/me")
    public ResponseEntity<UserResponse> getMyData(HttpServletRequest request) {

        User user;
        try {
            HttpSession session = request.getSession(false);
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<BoardListResponse> myBoards = userService.getMyBoards(user.getId());
        List<BoardListResponse> myApplyBoards = userService.getMyApplyBoards(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.builder()
                .user(user)
                .myBoards(myBoards)
                .myApplyBoards(myApplyBoards)
                .build());

    }

}
