package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IRolService;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final IRolService rolService;

    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/GetAllRoles")
    public ResponseEntity<List<RolResponse>> obtenerTodos() {

        return ResponseEntity.ok(
                rolService.obtenerTodos()
        );
    }

    @GetMapping("/GetRolesById/{id}")
    public ResponseEntity<RolResponse> obtenerPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(
                rolService.obtenerPorId(id)
        );
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolResponse> obtenerPorNombre(@PathVariable String nombre) {

        return ResponseEntity.ok(
                rolService.obtenerPorNombre(nombre)
        );
    }

    @PostMapping("/CreateRoles")
    public ResponseEntity<RolResponse> guardar(@RequestBody Rol rol) {

        return ResponseEntity.ok(
                rolService.guardar(rol)
        );
    }

    @PutMapping("/UpdateRoles/{id}")
    public ResponseEntity<RolResponse> actualizar(@PathVariable Integer id, @RequestBody Rol rol) {

        return ResponseEntity.ok(
                rolService.actualizar(id, rol)
        );
    }

    @DeleteMapping("/DeleteRoles/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Integer id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

