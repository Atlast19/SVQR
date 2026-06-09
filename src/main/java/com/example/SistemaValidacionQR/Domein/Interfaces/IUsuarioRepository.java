package com.example.SistemaValidacionQR.Domein.Interfaces;

import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository <Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByMatricula(String matricula);

    boolean existsByEmail(String email);

    boolean existsByMatricula(String matricula);
}
