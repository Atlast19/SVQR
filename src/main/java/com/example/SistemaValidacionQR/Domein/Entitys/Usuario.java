package com.example.SistemaValidacionQR.Domein.Entitys;

import com.example.SistemaValidacionQR.Domein.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Usuarios", schema = "dbo")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Matricula",nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(name = "Nombre",nullable = false, length = 100)
    private String nombre;

    @Column(name = "Apellido",nullable = false, length = 100)
    private String apellido;

    @Column(name = "Email",nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "PasswordHash",nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado",nullable = false)
    private EstadoUsuario estado;

    @Column(name = "UltimoAcceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<QrToken> qrTokens;

    @OneToMany(mappedBy = "usuario")
    private List<Acceso> accesos;
}
