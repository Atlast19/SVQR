package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IQrTokenService {

    GenerarQrResponse generarQrToken(Integer usuarioId, Integer eventoId);

    void revocarToken(Integer tokenId);

    void actualizarFechaExpiracionPorEvento(Integer eventoId, LocalDateTime nuevaFecha);

    List<QrTokenResponse> obtenerTokensPorUsuario(Integer usuarioId);
}
