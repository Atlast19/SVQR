package com.example.SistemaValidacionQR.Domein.Repository;

import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IQrTokenRepository extends JpaRepository <QrToken, Integer> {

    Optional<QrToken> findByToken(String tokenHash);

    List<QrToken> findByUsuarioId(Integer id);

    @Query("""
SELECT q
FROM QrToken q
WHERE q.usuario.id = :usuarioId
AND q.evento.id = :eventoId
AND q.revocado = false
AND q.usado = false
AND q.fechaExpiracion > CURRENT_TIMESTAMP
ORDER BY q.createdAt DESC
""")
    Optional<QrToken> findQrActivoPorUsuario(
            Integer usuarioId,
            Integer eventoId
    );


    @Query("""
SELECT q
FROM QrToken q
WHERE q.evento.id = :eventoId
AND q.usado = false
AND q.revocado = false
""")
    List<QrToken> obtenerQrActivosPorEvento(
            @Param("eventoId") Integer eventoId);


    List<QrToken> findByRevocadoFalseAndFechaExpiracionBefore(LocalDateTime fecha);
}