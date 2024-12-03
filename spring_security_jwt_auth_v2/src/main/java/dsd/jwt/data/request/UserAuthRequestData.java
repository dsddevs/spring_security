package dsd.jwt.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthRequestData implements UserAuthRequest {
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[\\w_!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!.,@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one lowercase, one uppercase letter, and one special character")
    private String password;
}

