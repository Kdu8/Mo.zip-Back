package kdu8.mozip.service;

import kdu8.mozip.entity.Board;
import kdu8.mozip.repository.ApplicantRepository;
import kdu8.mozip.repository.BoardRepository;
import kdu8.mozip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ApplicantRepository applicantRepository;

    public Page<Board> getBoardListWithPage(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}