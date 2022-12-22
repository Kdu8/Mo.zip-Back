package kdu8.mozip.presentation.dto.user;

import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    User user;

    List<BoardListResponse> myBoards;
    List<BoardListResponse> myApplyBoards;
}
