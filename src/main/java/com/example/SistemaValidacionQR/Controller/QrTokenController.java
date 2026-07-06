package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IQrTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qr")
public class QrTokenController {
    private final IQrTokenService qrTokenService;

    public QrTokenController(IQrTokenService QrTokenService ) {
        this.qrTokenService = QrTokenService;
    }

    @PostMapping("/generar/{usuarioId}/{eventoId}") // ESTUDIANTE - ADMINISTRADORES
    public ResponseEntity<GenerarQrResponse> generarQr(@PathVariable Integer usuarioId, @PathVariable Integer eventoId) {

        return ResponseEntity.ok(
                qrTokenService.generarQrToken(usuarioId, eventoId)
        );
    }

    @PostMapping("/revocar/{tokenId}") // ADMINISTRADOR
    public ResponseEntity<String> revocarQr(@PathVariable Integer tokenId) {

        qrTokenService.revocarToken(tokenId);

        return ResponseEntity.ok("QR revocado correctamente");
    }

    @GetMapping("/usuarios/{usuarioId}") // ESTUDIANETE - ADMINISTRADOR
    public ResponseEntity<List<QrTokenResponse>> obtenerTokensPorUsuario(@PathVariable Integer usuarioId) {

        return ResponseEntity.ok(
                qrTokenService.obtenerTokensPorUsuario(
                        usuarioId
                )
        );
    }
}
