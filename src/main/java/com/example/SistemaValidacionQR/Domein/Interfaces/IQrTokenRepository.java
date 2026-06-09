package com.example.SistemaValidacionQR.Domein.Interfaces;

import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IQrTokenRepository extends JpaRepository <QrToken, Integer> {

    Optional<QrToken> findByToken(String tokenHash);

    List<QrToken> findByUsuarioId(Integer id);
}
