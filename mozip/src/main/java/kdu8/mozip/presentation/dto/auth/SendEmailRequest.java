package kdu8.mozip.presentation.dto.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest {
        private String email;
}
