package com.example.SistemaValidacionQR.Domein.Repository;

import com.example.SistemaValidacionQR.Domein.Entitys.Evento;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IEventoRepository extends JpaRepository<Evento, Integer> {

    @Query("""
SELECT e
FROM Evento e
WHERE YEAR(e.createdAt) = :anio
ORDER BY e.id DESC
""")
    List<Evento> obtenerEventosDelAnio(
            @Param("anio") Integer anio);

    Optional<Evento> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Evento> findByEstado(EstadoGenerico estado);

    Optional<Evento> findById(Integer id);

}
