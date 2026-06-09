package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAccesoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accesos")
public class AccesoController {
    private final IAccesoService accesoService;

    public AccesoController(IAccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @GetMapping
    public ResponseEntity<List<AccesoResponse>>
    obtenerHistorial() {

        return ResponseEntity.ok(
                accesoService.obtenerHistorial()
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AccesoResponse>>
    obtenerHistorialUsuario(
            @PathVariable Integer usuarioId) {

        return ResponseEntity.ok(
                accesoService.obtenerHistorialUsuario(
                        usuarioId
                )
        );
    }

    @PostMapping
    public ResponseEntity<AccesoResponse>
    registrarAcceso(

            @RequestParam Integer usuarioId,
            @RequestParam Integer qrTokenId,
            @RequestParam String ip,
            @RequestParam String dispositivo) {

        return ResponseEntity.ok(
                accesoService.registrarAcceso(
                        usuarioId,
                        qrTokenId,
                        ip,
                        dispositivo
                )
        );
    }
}
