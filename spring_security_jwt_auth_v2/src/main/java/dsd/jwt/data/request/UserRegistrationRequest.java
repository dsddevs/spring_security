package dsd.jwt.data.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserRegistrationRequestData.class)
public interface UserRegistrationRequest {
    String getFullName();

    String getEmail();

    void setEmail(String email);

    String getPassword();

}
