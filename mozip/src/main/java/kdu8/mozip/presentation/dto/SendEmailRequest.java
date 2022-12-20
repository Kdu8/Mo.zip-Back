package kdu8.mozip.presentation.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@JsonAutoDetect
@AllArgsConstructor
public class SendEmailRequest {

        private String email;

}
