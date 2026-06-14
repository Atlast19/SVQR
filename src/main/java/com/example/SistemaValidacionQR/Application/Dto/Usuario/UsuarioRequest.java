package com.example.SistemaValidacionQR.Application.Dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(max = 20, message = "La matrícula no puede exceder los 20 caracteres")
    @Pattern(
            regexp = "^[0-9]+$",
            message = "La matrícula solo puede contener números"
    )
    private String matricula;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre no puede contener números ni caracteres especiales"
    )
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El apellido no puede contener números ni caracteres especiales"
    )
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 150, message = "El correo electrónico no puede exceder los 150 caracteres")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 255, message = "La contraseña no puede exceder los 255 caracteres")
    private String password;
}