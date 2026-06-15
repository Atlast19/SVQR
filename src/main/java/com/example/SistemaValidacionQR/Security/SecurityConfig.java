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


                        //ESTUDIANTE
                        .requestMatchers("/usuarios/UpdateUsuarioById/{id}").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        .requestMatchers("/qr/generar/{usuariosId}").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        .requestMatchers("/qr/validar").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        .requestMatchers("/accesos/registrar").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")
                        .requestMatchers("/qr/usuarios/{usuarioId}").hasAnyAuthority("ROLE_ESTUDIANTE", "ROLE_ADMINISTRADOR")

                        // Administración
                        .requestMatchers("/roles/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/GetAllUsuarios").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/GetUsuarioById/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/DeleteUsuario/{id}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/Email/{email}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/usuarios/Matricula/{matricula}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/qr/revocar/{tokenId}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/accesos/usuarios/{usuarioId}").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/accesos/GetHistorial").hasRole("ADMINISTRADOR")




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