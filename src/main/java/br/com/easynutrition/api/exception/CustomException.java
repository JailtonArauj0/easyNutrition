package br.com.easynutrition.api.exception;

import java.io.Serial;

public class CustomException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2831359556342869402L;

    public CustomException(String message) {
        super(message);
    }
}
