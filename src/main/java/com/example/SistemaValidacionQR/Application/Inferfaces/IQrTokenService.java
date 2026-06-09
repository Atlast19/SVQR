package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrValidationResponse;

import java.util.List;

public interface IQrTokenService {

    GenerarQrResponse generarQrToken(Integer usuarioId);

    QrValidationResponse validarQrToken(String token);

    void revocarToken(Integer tokenId);

    List<QrTokenResponse> obtenerTokensPorUsuario(Integer usuarioId);
}
