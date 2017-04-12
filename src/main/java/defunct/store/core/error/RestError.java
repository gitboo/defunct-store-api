package defunct.store.core.error;

import lombok.Data;

@Data
public class RestError {
    private final String code;
    private final String message;
    private final String developerMessage;
}