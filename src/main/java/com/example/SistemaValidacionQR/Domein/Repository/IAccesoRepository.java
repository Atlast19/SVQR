package com.example.SistemaValidacionQR.Domein.Repository;

import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAccesoRepository extends JpaRepository <Acceso, Integer> {

    List<Acceso> findByUsuarioId(Integer usuarioId);
}
