package kdu8.mozip.presentation.dto;

import kdu8.mozip.entity.User;
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
