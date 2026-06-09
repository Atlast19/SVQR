package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;

import java.util.List;

public interface IAccesoService {

    AccesoResponse registrarAcceso(Integer usuarioId, Integer qrTokenId, String ip, String dispositivo);

    List<AccesoResponse> obtenerHistorial();

    List<AccesoResponse> obtenerHistorialUsuario(Integer usuarioId);
}
