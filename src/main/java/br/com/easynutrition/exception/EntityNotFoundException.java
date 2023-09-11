package br.com.easynutrition.exception;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4092234213569444188L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
