package kdu8.mozip.service;

import kdu8.mozip.entity.Applicant;
import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.entity.VerifyCode;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import kdu8.mozip.repository.ApplicantRepository;
import kdu8.mozip.repository.BoardRepository;
import kdu8.mozip.repository.UserRepository;
import kdu8.mozip.repository.VerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VerifyCodeRepository verifyCodeRepository;

    private final BoardRepository boardRepository;

    private final ApplicantRepository applicantRepository;

    public void saveVerifyCode(String email, String verifyCode) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            int userId = user.get().getId();
            verifyCodeRepository.save(VerifyCode.builder().userId(userId).verifyCode(verifyCode).build());
        } else {
            throw new Exception("유저가 없음");
        }
    }

    public void join(String email, String name) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            userRepository.save(User.builder().name(name).email(email).build());
        } else {
            throw new Exception("유저가 있음");
        }
    }

    public User completeVerification(String verifyCode) throws Exception {
        Optional<VerifyCode> verifyCodeOptional = verifyCodeRepository.findByVerifyCode(verifyCode);
        if (verifyCodeOptional.isPresent()) {
            int userId = verifyCodeOptional.get().getUserId();
            User user = userRepository.findById(userId).get();
            return user;
        }
        else {
            throw new Exception("인증코드가 확인되지 않았습니다.");
        }
    }

    public List<BoardListResponse> getMyBoards(int userId) {

        List<Board> listBoard = boardRepository.findAllByWriterId(userId);

        List<BoardListResponse> dtoList = new ArrayList<>();

        for(Board board : listBoard ) {
            dtoList.add(BoardListResponse.getBoardListResponse(board, applicantRepository));
        }

        return dtoList;
    }

    public List<BoardListResponse> getMyApplyBoards(int userId) {

        List<Applicant> applicantList = applicantRepository.findAllByUserId(userId);
        List<BoardListResponse> dtoList = new ArrayList<>();

        for (Applicant applicant: applicantList) {
            Board board = boardRepository.findById(applicant.getBoardId()).get();
            dtoList.add(BoardListResponse.getBoardListResponse(board, applicantRepository));
        }



        return dtoList;
    }
}
