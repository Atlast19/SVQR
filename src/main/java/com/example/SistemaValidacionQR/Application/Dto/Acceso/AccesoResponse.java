package com.example.SistemaValidacionQR.Application.Dto.Acceso;

import com.example.SistemaValidacionQR.Domein.enums.EstadoAcceso;
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
    private Integer usuarioId;
    private String nombreCompleto;
    private LocalDateTime fechaAcceso;
    private EstadoAcceso estado;
    private String ipAddress;
    private String dispositivo;
}
