package dsd.jwt.data.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserAuthRequestData.class)
public interface UserAuthRequest {
    String getEmail();

    void setEmail(String email);

    String getPassword();

}
