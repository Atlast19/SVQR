package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IQrTokenService;
import com.example.SistemaValidacionQR.Domein.Entitys.Evento;
import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Repository.IEventoRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IQrTokenRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrTokenService implements IQrTokenService {

    private final IQrTokenRepository qrTokenRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IEventoRepository eventoRepository;

    public QrTokenService(IQrTokenRepository qrTokenRepository, IUsuarioRepository usuarioRepository, IEventoRepository eventoRepository) {
        this.qrTokenRepository = qrTokenRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @Override
    public GenerarQrResponse generarQrToken(Integer usuarioId, Integer eventoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .filter(u -> u.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuario no encontrado"
                        ));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));


        if (!LocalDateTime.now().isBefore(evento.getFechaInicio())) {
            throw new RuntimeException("La generación de códigos QR ha finalizado porque el evento ya inició.");
        }

        Optional<QrToken> qrActivo =
                qrTokenRepository.findQrActivoPorUsuario(usuarioId, eventoId);

        if (qrActivo.isPresent()
                && qrActivo.get()
                .getFechaExpiracion()
                .isAfter(LocalDateTime.now())) {

            QrToken qrExistente = qrActivo.get();

            GenerarQrResponse response = new GenerarQrResponse();
            response.setToken(qrExistente.getToken());
            response.setQrBase64(generarQrBase64(qrExistente.getToken()));
            response.setMatricula(qrExistente.getMatricula());
            response.setFechaExpiracion(qrExistente.getFechaExpiracion().toString());

            if (qrExistente.getEvento() != null) {
                response.setEventoId(
                        qrExistente.getEvento().getId()
                );
            }

            return response;
        }

        String token = UUID.randomUUID().toString();

        QrToken qrToken = new QrToken();

        qrToken.setToken(token);
        qrToken.setUsuario(usuario);
        qrToken.setEvento(evento);
        qrToken.setRevocado(false);
        qrToken.setUsado(false);
        qrToken.setCreatedAt(LocalDateTime.now());
        qrToken.setMatricula(usuario.getMatricula());
        qrToken.setCodigo(evento.getCodigo());
        qrToken.setFechaExpiracion(evento.getFechaExpiracion());

        qrTokenRepository.save(qrToken);

        GenerarQrResponse response = new GenerarQrResponse();

        response.setToken(token);
        response.setQrBase64(generarQrBase64(token));
        response.setFechaExpiracion(qrToken.getFechaExpiracion().toString());
        response.setMatricula(qrToken.getMatricula());
        response.setEventoId(evento.getId());
        response.setCodigo(qrToken.getCodigo());
        response.setUsuarioId(usuario.getId());

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
    @Transactional
    public void actualizarFechaExpiracionPorEvento(Integer eventoId, LocalDateTime nuevaFecha) {

        List<QrToken> qrTokens =
                qrTokenRepository.obtenerQrActivosPorEvento(eventoId);

        for (QrToken qr : qrTokens) {

            qr.setFechaExpiracion(nuevaFecha);
            qr.setUpdatedAt(LocalDateTime.now());
        }

            qrTokenRepository.saveAll(qrTokens);
    }

    @Override
    public List<QrTokenResponse> obtenerTokensPorUsuario(Integer usuarioId) {

        return qrTokenRepository
                .findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generarQrBase64(String contenido) {


        try {


            QRCodeWriter qrCodeWriter =
                    new QRCodeWriter();



            BitMatrix bitMatrix =
                    qrCodeWriter.encode(
                            contenido,
                            BarcodeFormat.QR_CODE,
                            300,
                            300
                    );



            BufferedImage bufferedImage =
                    MatrixToImageWriter.toBufferedImage(
                            bitMatrix
                    );



            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();



            ImageIO.write(
                    bufferedImage,
                    "png",
                    outputStream
            );



            byte[] imageBytes =
                    outputStream.toByteArray();



            return Base64.getEncoder()
                    .encodeToString(imageBytes);



        } catch (Exception e) {


            throw new RuntimeException(
                    "Error generando QR",
                    e
            );

        }

    }

    private QrTokenResponse mapToResponse(QrToken qrToken) {

        QrTokenResponse response =
                new QrTokenResponse();

        response.setId(qrToken.getId());
        response.setToken(qrToken.getToken());
        response.setFechaCreacion(qrToken.getCreatedAt());
        response.setFechaExpiracion(qrToken.getFechaExpiracion());

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
