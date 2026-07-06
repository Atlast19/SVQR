package com.example.SistemaValidacionQR.Application.Dto.QrToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerarQrResponse {

    private String token;
    private String qrBase64;
    private String matricula;
    private String fechaExpiracion;
    private Integer eventoId;
    private String codigo;
    private Integer usuarioId;
}
