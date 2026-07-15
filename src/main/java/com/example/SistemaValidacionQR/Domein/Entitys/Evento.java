package com.example.SistemaValidacionQR.Domein.Entitys;

import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Eventos", schema = "dbo")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El código es obligatorio")
    @Size(min = 3, max = 10,
            message = "El código debe tener entre 3 y 10 caracteres")
    @Column(name = "Codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100,
            message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500,
            message = "La descripción no puede exceder los 500 caracteres")
    @Column(name = "Descripcion", nullable = false, length = 500)
    private String descripcion;

    private String imagen;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "FechaInicio", nullable = false)
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Column(name = "FechaExpiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false, length = 30)
    private EstadoGenerico estado;

    @Column(name = "CreatedAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;
}
