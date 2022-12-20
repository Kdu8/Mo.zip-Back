package kdu8.mozip.service;

import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.SignInRequest;
import kdu8.mozip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public User login(SignInRequest request) throws Exception {
//        User user = userRepository.findAllByEmail(request.getEmail()).orElseThrow(()-> new Exception("존재하지 않는 이메일입니다"));
//
//        return User.builder()
//                .name(user.getName())
//                .email(user.getEmail())
//                .token(user.getToken())
//                .build();
//    }
}
