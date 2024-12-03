package dsd.jwt.data.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ServerAuthResponseData.class)
public interface ServerAuthResponse {
}
