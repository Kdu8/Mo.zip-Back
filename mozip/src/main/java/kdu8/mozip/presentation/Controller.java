package kdu8.mozip.presentation;

import io.swagger.annotations.*;
import kdu8.mozip.entity.User;
import kdu8.mozip.presentation.dto.SendEmailRequest;
import kdu8.mozip.presentation.dto.SignInRequest;
import kdu8.mozip.service.EmailService;
import kdu8.mozip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class Controller {

    @Autowired
    private final EmailService emailService;

    @PostMapping("/emailConfirm")
    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> emailConfirm(
            @RequestBody SendEmailRequest email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email.getEmail());

        return new ResponseEntity("success", HttpStatus.OK);
    }

}


//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<Void> login(@RequestBody SignInRequest request) throws Exception {
//        User data = userService.login(request);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }