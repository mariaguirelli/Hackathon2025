package edu.unialfa.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// Remova qualquer "abstract" aqui!
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
