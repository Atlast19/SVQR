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

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false)
    private Boolean revocado;

    @Column(nullable = false)
    private Boolean usado;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    private Usuario usuario;

    @OneToMany
    private List<Acceso> accesos;
}
