package kdu8.mozip.service;

import kdu8.mozip.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {


    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserServiceTest(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Test
    void authProcessTest() {

        String email = "kimww0306@gmail.com";
        String name = "김원욱";

        try {
            userService.join(email, name);
        } catch (Exception e) {
            fail("유저 이미 존재");
        }

        String verifyCode = null;
        try {
            verifyCode = emailService.sendSimpleMessage(email);
        } catch (Exception e) {
            fail("메일 보내기 실패");
        }

        try {
            userService.saveVerifyCode(email, verifyCode);
        } catch (Exception e) {
            fail("인증코드 저장 실패");
        }

        try {
            User user = userService.completeVerification(verifyCode);
            assertThat(user.getEmail()).isEqualTo(email);
        } catch (Exception e) {
            fail("맞는 유저 찾기 실패");
        }

    }
}