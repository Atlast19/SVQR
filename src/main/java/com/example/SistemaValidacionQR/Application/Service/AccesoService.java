package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAccesoService;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import com.example.SistemaValidacionQR.Domein.Entitys.Evento;
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

        QrToken qrToken = qrTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("QR no encontrado"));

        // Validar si ya fue utilizado
        if (qrToken.getUsado()) {
            throw new RuntimeException("QR ya utilizado");
        }

        // Validar si está revocado o expirado
        if (qrToken.getRevocado()
                || LocalDateTime.now().isAfter(qrToken.getFechaExpiracion())) {
            throw new RuntimeException("QR expirado o revocado");
        }

        Usuario usuario = qrToken.getUsuario();

        Evento evento = qrToken.getEvento();

        // Marcar QR como utilizado
        qrToken.setUsado(true);
        qrToken.setUpdatedAt(LocalDateTime.now());

        qrTokenRepository.save(qrToken);

        // Registrar acceso
        Acceso acceso = new Acceso();

        acceso.setUsuario(usuario);
        acceso.setQrToken(qrToken);
        acceso.setEvento(evento);

        acceso.setMatricula(usuario.getMatricula());
        acceso.setIpAddress(request.getRemoteAddr());
        acceso.setDispositivo(request.getHeader("User-Agent"));
        acceso.setFechaAcceso(LocalDateTime.now());
        acceso.setEstado(EstadoAcceso.APROBADO);

        acceso = accesoRepository.save(acceso);

        // Construir respuesta
        AccesoResponse response = new AccesoResponse();

        response.setId(acceso.getId());

        response.setUsuarioId(usuario.getId());

        response.setMatricula(usuario.getMatricula());

        response.setNombreCompleto(
                usuario.getNombre()
                        + " "
                        + usuario.getApellido()
        );

        response.setFechaAcceso(acceso.getFechaAcceso());

        response.setEstado(acceso.getEstado());

        response.setIpAddress(acceso.getIpAddress());

        response.setDispositivo(acceso.getDispositivo());

        if (evento != null) {

            response.setEventoId(evento.getId());

            response.setNombreEvento(
                    evento.getNombre()
            );

            response.setCodigo(
                    evento.getCodigo()
            );
        }

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
