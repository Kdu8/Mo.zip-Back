package kdu8.mozip.service;

import kdu8.mozip.entity.Applicant;
import kdu8.mozip.entity.Board;
import kdu8.mozip.repository.ApplicantRepository;
import kdu8.mozip.repository.BoardRepository;
import kdu8.mozip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicantService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;

    public void toggleApply(Board board, int userId) throws Exception {

        List<Applicant> applicants = applicantRepository.findAllByBoardId(board.getId());

        if(board.getWriterId() == userId) {
            throw new Exception("작성자는 신청할 수 없습니다.");
        }

        if(board.isFinished()) {
            throw new Exception("마감되었습니다.");
        }

        for (Applicant applicant : applicants) {
            if (applicant.getUserId() == userId && board.getExDate().isBefore(LocalDateTime.now())) {
                // 이미 신청함(신청 취소 필요)
                applicantRepository.delete(applicant);
                board.setFinished(false);
                return;
            }
        }
        //신청 안함(신청 필요)
        applicantRepository.save(Applicant.builder()
                .userId(userId)
                .boardId(board.getId())
                .build());

        if(applicants.size() + 1 == board.getMaxApp()) {
            board.setFinished(true);
        }
    }

}
