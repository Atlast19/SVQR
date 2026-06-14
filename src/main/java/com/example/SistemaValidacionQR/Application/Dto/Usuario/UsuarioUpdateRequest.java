package com.example.SistemaValidacionQR.Application.Dto.Usuario;

import jakarta.validation.constraints.Email;
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
public class UsuarioUpdateRequest {

    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El nombre no puede contener números ni caracteres especiales"
    )
    private String nombre;

    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
    @Pattern(
            regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$",
            message = "El apellido no puede contener números ni caracteres especiales"
    )
    private String apellido;

    @Size(max = 150, message = "El correo no puede exceder los 150 caracteres")
    @Email(message = "Debe proporcionar un correo válido")
    private String email;

    @Size(max = 255, message = "La contraseña no puede exceder los 255 caracteres")
    private String password;
}
