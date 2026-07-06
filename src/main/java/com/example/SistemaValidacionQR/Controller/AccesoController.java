package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IAccesoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accesos")
public class AccesoController {
    private final IAccesoService accesoService;

    public AccesoController(IAccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @GetMapping("/GetHistorial") // ADMINISTRADOR
    public ResponseEntity<List<AccesoResponse>> obtenerHistorial() {

        return ResponseEntity.ok(accesoService.obtenerHistorial());
    }

    @GetMapping("/usuarios/{usuarioId}") // ESTUDIANTE - ADMINISTRADOR
    public ResponseEntity<List<AccesoResponse>> obtenerHistorialUsuario(@PathVariable Integer usuarioId) {

        return ResponseEntity.ok(accesoService.obtenerHistorialUsuario(usuarioId));
    }

    @PostMapping("/registrar") // ADMINISTRADOR
    public ResponseEntity<AccesoResponse> registrarAcceso(@RequestParam String token, HttpServletRequest request) {

        return ResponseEntity.ok(accesoService.registrarAcceso(token, request));
    }
}
