package com.example.SistemaValidacionQR.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Login público
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuarios/CreateUsuarios").permitAll()


                        //Actualizar Usuario
                        .requestMatchers("/usuarios/UpdateUsuarioById/{id}").hasAnyAuthority("ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        //Generar el QR
                        .requestMatchers("/qr/generar/{usuariosId}/{eventoId}").hasAnyAuthority("ROLE_ESTUDIANTE", "ADMINISTRADOR")
                        //QR generados por el Usuario
                        .requestMatchers("/qr/usuarios/{usuarioId}").hasAnyAuthority("ESTUDIANTE", "ADMINISTRADOR")
                        //Historial de Acceso
                        .requestMatchers("/accesos/usuarios/{usuarioId}").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        // Buscar eventos por nombre
                        .requestMatchers("/api/eventos/GetEventoByNombre/{nombre}").hasAnyAuthority("ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        // Buscar evento por codigo
                        .requestMatchers("/api/eventos/GetEventoByCodigo/{codigo}").hasAnyAuthority("ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        // Ver todos los eventos
                        .requestMatchers("/api/eventos/GetAllEventos").hasAnyRole("ESTUDIANTE", "ADMINISTRADOR")



                        // Administración
                        .requestMatchers("/roles/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/GetAllUsuarios").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/GetUsuarioById/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/DeleteUsuario/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/Email/{email}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/Matricula/{matricula}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/qr/revocar/{tokenId}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/accesos/GetHistorial").hasRole("ADMINISTRADOR")
                        .requestMatchers("/accesos/registrar").hasRole("ADMINISTRADOR")
                        .requestMatchers("/eventos/CrearEvento").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/eventos/UpdateEvento/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/eventos/GetEventoById/{id}").hasRole("ADMINISTRADOR")




                        .requestMatchers("/error").permitAll()


                        // Todo lo demás requiere JWT
                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
   }
}