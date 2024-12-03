package dsd.jwt.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServerErrorResponseData implements ServerErrorResponse {
    private String errorCode;
    private String errorMessage;
}
