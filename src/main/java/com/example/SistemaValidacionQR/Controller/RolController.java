package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IRolService;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final IRolService rolService;

    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<RolResponse>> obtenerTodos() {

        return ResponseEntity.ok(
                rolService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> obtenerPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                rolService.obtenerPorId(id)
        );
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolResponse> obtenerPorNombre(
            @PathVariable String nombre) {

        return ResponseEntity.ok(
                rolService.obtenerPorNombre(nombre)
        );
    }

    @PostMapping
    public ResponseEntity<Rol> guardar(
            @RequestBody Rol rol) {

        return ResponseEntity.ok(
                rolService.guardar(rol)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(
            @PathVariable Integer id,
            @RequestBody Rol rol) {

        return ResponseEntity.ok(
                rolService.actualizar(id, rol)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

