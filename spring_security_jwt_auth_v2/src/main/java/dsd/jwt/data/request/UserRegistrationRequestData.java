package dsd.jwt.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationRequestData extends UserAuthRequestData implements UserRegistrationRequest {
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 20)
    private String fullName;
}
