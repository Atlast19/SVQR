package com.example.SistemaValidacionQR.Domein.Entitys;

import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Roles", schema = "dbo")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "CreatedAt", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado",nullable = false)
    private EstadoGenerico estado;

    @OneToMany
    private List<Usuario> usuarios;
}
