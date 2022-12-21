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

        boolean exists = applicantRepository.existsByBoardId(board.getId());
        int applicantCount = 1;

        if (board.getWriterId() != userId) {
            if (board.isFinished()) {
                if(exists) {

                    List<Applicant> applicants = applicantRepository.findAllByBoardId(board.getId());


                    for (Applicant applicant : applicants) {
                        if (applicant.getUserId() == userId && board.getExDate().isBefore(LocalDateTime.now())) {
                            // 이미 신청함(신청 취소 필요)
                            applicantRepository.delete(applicant);
                            board.setFinished(false);
                            return;
                        }
                    }

                    applicantCount += applicants.size();
                }
                //신청 안함(신청 필요)
                applicantRepository.save(Applicant.builder()
                        .userId(userId)
                        .boardId(board.getId())
                        .build());

                if(applicantCount == board.getMaxApp()) {
                    board.setFinished(true);
                }

            } else {
                throw new Exception("마감되었습니다.");
            }

        } else {
            throw new Exception("작성자는 신청할 수 없습니다.");
        }




    }

}
