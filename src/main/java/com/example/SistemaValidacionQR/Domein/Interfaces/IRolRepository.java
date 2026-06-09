package com.example.SistemaValidacionQR.Domein.Interfaces;

import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository <Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
