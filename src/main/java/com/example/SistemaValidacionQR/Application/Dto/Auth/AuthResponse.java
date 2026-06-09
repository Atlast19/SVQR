package com.example.SistemaValidacionQR.Application.Dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private Integer usuarioId;
    private String nombre;
    private String email;
    private String rol;
}
