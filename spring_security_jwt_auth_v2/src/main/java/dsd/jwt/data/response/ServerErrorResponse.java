package dsd.jwt.data.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ServerErrorResponseData.class)
public interface ServerErrorResponse {
}
