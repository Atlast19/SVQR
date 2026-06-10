package com.example.SistemaValidacionQR.Application.Dto.Rol;

import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private EstadoGenerico estado;
}
