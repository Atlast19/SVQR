package com.example.SistemaValidacionQR.Application.Dto.Eventos;

import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100,
            message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500,
            message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;


    private String imagen;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de expiración es obligatoria")
    private LocalDateTime fechaExpiracion;

    private EstadoGenerico estado;

    @AssertTrue(message = "La fecha de expiración debe ser posterior a la fecha de inicio")
    public boolean isFechaValida() {

        if (fechaInicio == null || fechaExpiracion == null) {
            return true;
        }

        return fechaExpiracion.isAfter(fechaInicio);
    }

}
