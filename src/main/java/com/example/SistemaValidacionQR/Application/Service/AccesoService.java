package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAccesoService;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Interfaces.IAccesoRepository;
import com.example.SistemaValidacionQR.Domein.Interfaces.IQrTokenRepository;
import com.example.SistemaValidacionQR.Domein.Interfaces.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoAcceso;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AccesoService implements IAccesoService {

    private final IAccesoRepository accesoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IQrTokenRepository qrTokenRepository;

    public AccesoService(IAccesoRepository accesoRepository, IUsuarioRepository usuarioRepository, IQrTokenRepository qrTokenRepository) {
        this.accesoRepository = accesoRepository;
        this.usuarioRepository = usuarioRepository;
        this.qrTokenRepository = qrTokenRepository;
    }

    @Override
    public AccesoResponse registrarAcceso(
            Integer usuarioId,
            Integer qrTokenId,
            String ip,
            String dispositivo) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        QrToken qrToken = qrTokenRepository.findById(qrTokenId)
                .orElseThrow(() ->
                        new RuntimeException("QR no encontrado"));

        Acceso acceso = new Acceso();

        acceso.setUsuario(usuario);
        acceso.setQrToken(qrToken);
        acceso.setIpAddress(ip);
        acceso.setDispositivo(dispositivo);
        acceso.setFechaAcceso(LocalDateTime.now());
        acceso.setEstado(EstadoAcceso.APROBADO);

        qrToken.setUsado(true);
        qrTokenRepository.save(qrToken);

        Acceso accesoGuardado =
                accesoRepository.save(acceso);

        return mapToResponse(accesoGuardado);
    }

    @Override
    public List<AccesoResponse> obtenerHistorial() {

        return accesoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AccesoResponse> obtenerHistorialUsuario(
            Integer usuarioId) {

        return accesoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AccesoResponse mapToResponse(
            Acceso acceso) {

        AccesoResponse response =
                new AccesoResponse();

        response.setId(acceso.getId());

        response.setMatricula(
                acceso.getUsuario().getMatricula()
        );

        response.setNombreCompleto(
                acceso.getUsuario().getNombre()
                        + " " +
                        acceso.getUsuario().getApellido()
        );

        response.setFechaAcceso(
                acceso.getFechaAcceso()
        );

        response.setEstado(
                acceso.getEstado().name()
        );

        response.setIpAddress(
                acceso.getIpAddress()
        );

        response.setDispositivo(
                acceso.getDispositivo()
        );

        return response;
    }
}
