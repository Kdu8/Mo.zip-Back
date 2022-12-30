package kdu8.mozip.service;

import kdu8.mozip.entity.Applicant;
import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.entity.VerifyCode;
import kdu8.mozip.exception.UserDoesntExistException;
import kdu8.mozip.exception.UserExistException;
import kdu8.mozip.exception.VerifyCodeNotFoundException;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import kdu8.mozip.repository.ApplicantRepository;
import kdu8.mozip.repository.BoardRepository;
import kdu8.mozip.repository.UserRepository;
import kdu8.mozip.repository.VerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public void saveVerifyCode(String email, String verifyCode) {
        User user = userRepository.findByEmail(email).get();
        int userId = user.getId();
        verifyCodeRepository.save(VerifyCode.builder().userId(userId).verifyCode(verifyCode).build());
    }

    public void join(String email, String name, String fullName) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            userRepository.save(User.builder().name(name).email(email).discord(fullName).build());
        } else {
            throw new UserExistException("유저가 있음");
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
            throw new VerifyCodeNotFoundException("인증코드가 확인되지 않았습니다.");
        }
    }

    public List<BoardListResponse> getMyBoards(int userId) {

        List<Board> listBoard = boardRepository.findAllByWriterId(userId);

        List<BoardListResponse> dtoList = new ArrayList<>();

        for(Board board : listBoard ) {
            dtoList.add(BoardListResponse.getBoardListResponse(board, applicantRepository, userRepository));
        }

        return dtoList;
    }

    public List<BoardListResponse> getMyApplyBoards(int userId) {

        List<Applicant> applicantList = applicantRepository.findAllByUserId(userId);
        List<BoardListResponse> dtoList = new ArrayList<>();

        for (Applicant applicant: applicantList) {
            Board board = boardRepository.findById(applicant.getBoardId()).get();
            dtoList.add(BoardListResponse.getBoardListResponse(board, applicantRepository, userRepository));
        }

        return dtoList;
    }

    public User authUser(HttpServletRequest request) throws UserDoesntExistException{
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            return user;
        } catch (Exception e) {
            throw new UserDoesntExistException("유저가 존재하지 않음");
        }
    }

    public boolean checkUserExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
