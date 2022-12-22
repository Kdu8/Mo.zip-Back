package kdu8.mozip.presentation.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Getter
@RequiredArgsConstructor
public class SignInRequest {
    private final String email;
}
