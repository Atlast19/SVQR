package com.example.SistemaValidacionQR.Application.Dto.Eventos;

import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponse {
    private Integer id;
    private String Codigo;
    private String nombre;
    private String descripcion;
    private String imagen;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaExpiracion;
    private EstadoGenerico estado;
}
