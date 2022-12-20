package kdu8.mozip.presentation;

import io.swagger.annotations.*;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.RegisterRequest;
import kdu8.mozip.presentation.dto.SendEmailRequest;
import kdu8.mozip.presentation.dto.VerifyCodeRequest;
import kdu8.mozip.service.EmailService;
import kdu8.mozip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(
            @RequestBody RegisterRequest registerRequest) throws Exception {
        String emailAddress = registerRequest.getEmail();
        userService.join(emailAddress, registerRequest.getName());
        String verifyCode = emailService.sendSimpleMessage(emailAddress);
        userService.saveVerifyCode(emailAddress, verifyCode);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/emailVerify")
    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> emailVerify(
            @RequestBody SendEmailRequest email) throws Exception {

        // 이메일을 보낸 후 사용자가 있는 지 검증함. 바꿔야 함.
        String emailAddress = email.getEmail();
        String verifyCode = emailService.sendSimpleMessage(emailAddress);
        userService.saveVerifyCode(emailAddress, verifyCode);

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/emailConfirm")
    @ApiOperation(value = "인증코드 확인", notes = "받은 인증코드가 존재하는지 확인 후 세션 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<User> emailConfirm(
            @RequestBody VerifyCodeRequest verifyCode, HttpServletRequest request) throws Exception {
        User user = userService.completeVerification(verifyCode.getVerifyCode());
        HttpSession session = request.getSession();

        session.setAttribute("user", user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }

}


//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<Void> login(@RequestBody SignInRequest request) throws Exception {
//        User data = userService.login(request);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }