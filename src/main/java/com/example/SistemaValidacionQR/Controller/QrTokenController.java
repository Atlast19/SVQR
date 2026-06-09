package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.QrToken.GenerarQrResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrTokenResponse;
import com.example.SistemaValidacionQR.Application.Dto.QrToken.QrValidationResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IQrTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qr")
public class QrTokenController {
    private final IQrTokenService qrTokenService;

    public QrTokenController(IQrTokenService QrTokenService ) {
        this.qrTokenService = QrTokenService;
    }

    @PostMapping("/generar/{usuarioId}")
    public ResponseEntity<GenerarQrResponse> generarQr(
            @PathVariable Integer usuarioId) {

        return ResponseEntity.ok(
                qrTokenService.generarQrToken(usuarioId)
        );
    }

    @GetMapping("/validar")
    public ResponseEntity<QrValidationResponse> validarQr(
            @RequestParam String token) {

        return ResponseEntity.ok(
                qrTokenService.validarQrToken(token)
        );
    }

    @PostMapping("/revocar/{tokenId}")
    public ResponseEntity<String> revocarQr(
            @PathVariable Integer tokenId) {

        qrTokenService.revocarToken(tokenId);

        return ResponseEntity.ok(
                "QR revocado correctamente"
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<QrTokenResponse>>
    obtenerTokensPorUsuario(
            @PathVariable Integer usuarioId) {

        return ResponseEntity.ok(
                qrTokenService.obtenerTokensPorUsuario(
                        usuarioId
                )
        );
    }
}
