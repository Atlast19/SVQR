package com.example.SistemaValidacionQR.Domein.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QrTokens", schema = "dbo")
public class QrToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "Matricula",nullable = false, unique = true, length = 20)
    private  String matricula;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false)
    private Boolean revocado;

    @Column(nullable = false)
    private Boolean usado;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "EventoId", nullable = false)
    private Evento evento;

    @Column(name = "Codigo", nullable = false, unique = true, length = 10)
    private  String Codigo;

    @OneToMany
    private List<Acceso> accesos;
}
