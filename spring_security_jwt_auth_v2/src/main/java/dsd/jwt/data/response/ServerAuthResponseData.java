package dsd.jwt.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
@Data
public class ServerAuthResponseData implements ServerAuthResponse {
    @JsonProperty
    private String accessJwt;
    @JsonProperty
    private String refreshJwt;
    private boolean success;
    private String errorMessage;
}
