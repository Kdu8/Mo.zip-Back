package kdu8.mozip.service;

import kdu8.mozip.entity.User;
import kdu8.mozip.entity.VerifyCode;
import kdu8.mozip.presentation.dto.SignInRequest;
import kdu8.mozip.repository.UserRepository;
import kdu8.mozip.repository.VerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VerifyCodeRepository verifyCodeRepository;

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
