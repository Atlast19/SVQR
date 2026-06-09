package com.example.SistemaValidacionQR.Application.Dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    private String matricula;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
