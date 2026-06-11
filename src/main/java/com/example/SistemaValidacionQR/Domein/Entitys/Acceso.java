package com.example.SistemaValidacionQR.Domein.Entitys;

import com.example.SistemaValidacionQR.Domein.enums.EstadoAcceso;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Accesos", schema = "dbo")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fechaAcceso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAcceso estado;

    @Column(length = 50)
    private String ipAddress;

    @Column(length = 255)
    private String dispositivo;

    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    private Usuario usuario;

    @Column(name = "Matricula",nullable = false, unique = true, length = 20)
    private  String matricula;

    @ManyToOne
    @JoinColumn(name = "QrTokenId", nullable = false)
    private QrToken qrToken;
}
