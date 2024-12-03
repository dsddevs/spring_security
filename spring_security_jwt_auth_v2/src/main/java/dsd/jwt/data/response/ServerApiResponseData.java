package dsd.jwt.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ServerApiResponseData<T> {
    private boolean success;
    private T data;
    private String errorCode;
    private String errorMessage;
}
