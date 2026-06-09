package com.example.SistemaValidacionQR.Application.Dto.QrToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class QrValidationResponse {
    private boolean valido;
    private String mensaje;
    private String matricula;
    private String nombreCompleto;
}
