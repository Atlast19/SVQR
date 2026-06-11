package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAccesoService;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Repository.IAccesoRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IQrTokenRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoAcceso;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AccesoService implements IAccesoService {

    private final IAccesoRepository accesoRepository;
    private final IQrTokenRepository qrTokenRepository;

    public AccesoService(IAccesoRepository accesoRepository, IQrTokenRepository qrTokenRepository) {
        this.accesoRepository = accesoRepository;

        this.qrTokenRepository = qrTokenRepository;
    }

    @Override
    public AccesoResponse registrarAcceso(String token, HttpServletRequest request) {

        QrToken qrToken = qrTokenRepository
                .findByToken(token)
                .filter(qrTokens -> !qrTokens.getRevocado())
                .filter(qrTokens -> !qrTokens.getUsado())
                .orElseThrow(() ->
                        new RuntimeException("QR no encontrado"));

        Usuario usuario = qrToken.getUsuario();


        String ip = request.getRemoteAddr();

        String dispositivo = request.getHeader("User-Agent");

        Acceso acceso = new Acceso();

        acceso.setUsuario(usuario);
        acceso.setQrToken(qrToken);
        acceso.setMatricula(usuario.getMatricula());
        acceso.setIpAddress(ip);
        acceso.setDispositivo(dispositivo);
        acceso.setFechaAcceso(LocalDateTime.now());
        acceso.setEstado(EstadoAcceso.APROBADO);

        accesoRepository.save(acceso);

        AccesoResponse response =
                new AccesoResponse();

        response.setId(acceso.getId());
        response.setUsuarioId(usuario.getId());
        response.setMatricula(usuario.getMatricula());
        response.setFechaAcceso(acceso.getFechaAcceso());
        response.setMatricula(acceso.getMatricula());
        response.setIpAddress(acceso.getIpAddress());
        response.setDispositivo(acceso.getDispositivo());
        response.setEstado(acceso.getEstado());


        return response;
    }

    @Override
    public List<AccesoResponse> obtenerHistorial() {

        return accesoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AccesoResponse> obtenerHistorialUsuario(Integer usuarioId) {

        return accesoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AccesoResponse mapToResponse(Acceso acceso) {

        AccesoResponse response =
                new AccesoResponse();

        response.setId(
                acceso.getId()
        );

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
                EstadoAcceso.valueOf(acceso.getEstado().name())
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
