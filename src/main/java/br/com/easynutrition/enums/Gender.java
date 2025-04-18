package br.com.easynutrition.enums;

import br.com.easynutrition.exception.CustomException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    MASCULINO('M'),
    FEMININO('F');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public static Gender fromCode(char code) {
        return Arrays.stream(values())
                .filter(g -> g.code == code)
                .findFirst()
                .orElseThrow(() -> new CustomException("Valor '" + code + "' inválido para o campo sex, valores aceitos: 'M' ou 'F'"));
    }
}
