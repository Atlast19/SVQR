package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Auth.AuthRequest;
import com.example.SistemaValidacionQR.Application.Dto.Auth.AuthResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAuthService;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Repository.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import com.example.SistemaValidacionQR.Security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final IUsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService customUserDetailsService;

    public AuthService(IUsuarioRepository usuarioRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserDetailsService customUserDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        System.out.println("Estado del usuario: " + usuario.getEstado());

        if (usuario.getEstado() == EstadoGenerico.INACTIVO) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(request.getEmail());

        String token =
                jwtService.generateToken(userDetails);

        AuthResponse response =
                new AuthResponse();

        response.setToken(token);
        response.setUsuarioId(usuario.getId());
        response.setNombre(
                usuario.getNombre() + " " +
                        usuario.getApellido()
        );
        response.setEmail(usuario.getEmail());
        response.setRol(
                usuario.getRol().getNombre()
        );

        return response;
    }
}
