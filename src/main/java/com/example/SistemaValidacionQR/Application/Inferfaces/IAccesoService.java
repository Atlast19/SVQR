package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IAccesoService {

    AccesoResponse registrarAcceso(String token, HttpServletRequest request);

    List<AccesoResponse> obtenerHistorial();

    List<AccesoResponse> obtenerHistorialUsuario(Integer usuarioId);
}
