package dsd.spring_security_jwt_auth.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;
}
