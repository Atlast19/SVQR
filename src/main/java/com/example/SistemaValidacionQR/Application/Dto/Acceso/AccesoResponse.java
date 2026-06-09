package com.example.SistemaValidacionQR.Application.Dto.Acceso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccesoResponse {

    private Integer id;
    private String matricula;
    private String nombreCompleto;
    private LocalDateTime fechaAcceso;
    private String estado;
    private String ipAddress;
    private String dispositivo;
}
