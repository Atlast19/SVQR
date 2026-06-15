package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrValidationResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IQrTokenService;
import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Repository.IQrTokenRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QrTokenService implements IQrTokenService {

    private final IQrTokenRepository qrTokenRepository;
    private final IUsuarioRepository usuarioRepository;

    public QrTokenService(IQrTokenRepository qrTokenRepository, IUsuarioRepository usuarioRepository) {
        this.qrTokenRepository = qrTokenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public GenerarQrResponse generarQrToken(Integer usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuario no encontrado"));

        String token =
                UUID.randomUUID().toString();

        QrToken qrToken = new QrToken();

        qrToken.setToken(token);
        qrToken.setUsuario(usuario);
        qrToken.setRevocado(false);
        qrToken.setUsado(false);
        qrToken.setCreatedAt(LocalDateTime.now());

        qrToken.setMatricula(usuario.getMatricula());

        qrToken.setFechaExpiracion(
                LocalDateTime.now().plusMinutes(10)
        );

        qrTokenRepository.save(qrToken);

        GenerarQrResponse response =
                new GenerarQrResponse();

        response.setToken(token);

        // temporalmente vacío
        response.setQrBase64("");

        response.setFechaExpiracion(
                qrToken.getFechaExpiracion().toString()
        );

        return response;
    }

    @Override
    public QrValidationResponse validarQrToken(String token) {

        QrToken qrToken =
                qrTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "QR no encontrado"));

        QrValidationResponse response =
                new QrValidationResponse();

        if (qrToken.getUsado()) {

            response.setValido(false);
            response.setMensaje("QR ya utilizado");

            return response;
        }

        if (qrToken.getRevocado() ||
                LocalDateTime.now().isAfter(
                        qrToken.getFechaExpiracion())) {

            response.setValido(false);
            response.setMensaje("Token expirado");

            return response;
        }

        Usuario usuario = qrToken.getUsuario();


        qrToken.setUpdatedAt(LocalDateTime.now());
        qrToken.setUsado(true);

        qrTokenRepository.save(qrToken);

        response.setValido(true);
        response.setMensaje("QR válido");

        response.setMatricula(
                usuario.getMatricula()
        );

        response.setNombreCompleto(
                usuario.getNombre()
                        + " "
                        + usuario.getApellido()
        );

        return response;
    }

    @Override
    public void revocarToken(Integer tokenId) {

        QrToken qrToken =
                qrTokenRepository.findById(tokenId)
                        .filter(qrTokens -> !qrTokens.getRevocado())
                        .filter(qrTokens -> !qrTokens.getUsado())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Token no encontrado"));

        qrToken.setRevocado(true);
        qrToken.setUpdatedAt(LocalDateTime.now());

        qrTokenRepository.save(qrToken);
    }

    @Override
    public List<QrTokenResponse> obtenerTokensPorUsuario(Integer usuarioId) {

        return qrTokenRepository
                .findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private QrTokenResponse mapToResponse(QrToken qrToken) {

        QrTokenResponse response =
                new QrTokenResponse();

        response.setId(
                qrToken.getId()
        );

        response.setToken(
                qrToken.getToken()
        );

        response.setFechaCreacion(
                qrToken.getCreatedAt()
        );

        response.setFechaExpiracion(
                qrToken.getFechaExpiracion()
        );

        if (qrToken.getRevocado()) {

            response.setEstado("REVOCADO");

        } else if (qrToken.getUsado()) {

            response.setEstado("USADO");

        } else if (
                qrToken.getFechaExpiracion()
                        .isBefore(LocalDateTime.now())
        ) {

            response.setEstado("EXPIRADO");

        } else {

            response.setEstado("ACTIVO");
        }

        return response;
    }

}
