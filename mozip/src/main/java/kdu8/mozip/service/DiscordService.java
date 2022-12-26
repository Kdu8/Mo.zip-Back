package kdu8.mozip.service;

import kdu8.mozip.entity.Applicant;
import kdu8.mozip.entity.Board;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.board.BoardListResponse;
import kdu8.mozip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DiscordService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepository userRepository;

    public void sendNewBoardNotification(Board board) {
        simpMessagingTemplate.convertAndSend("/queue/board", board);
    }

    public void sendApplicantChangeNotification(BoardListResponse boardListResponse, Boolean isApply) {
        // BoardListResponse 타입으로 전송

        HashMap<String, Object> message = new HashMap<>();
        message.put("Board", boardListResponse);
        message.put("isApply", isApply);

        User user = userRepository.findById(boardListResponse.getWriterId()).get();
        message.put("discord", user.getDiscord());

        simpMessagingTemplate.convertAndSend("/queue/applicant", message);
    }

}
