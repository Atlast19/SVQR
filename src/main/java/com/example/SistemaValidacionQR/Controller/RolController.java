package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolRequest;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolUpdateRequest;
import com.example.SistemaValidacionQR.Application.Inferfaces.IRolService;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import jakarta.validation.Valid;
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
    //Todos son de ADMINISTRADOR
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
    public ResponseEntity<RolResponse> guardar(@Valid @RequestBody RolRequest request) {

        return ResponseEntity.ok(rolService.guardar(request));
    }

    @PutMapping("/UpdateRoles/{id}")
    public ResponseEntity<RolResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody RolUpdateRequest request) {

        return ResponseEntity.ok(rolService.actualizar(id, request));
    }

    @DeleteMapping("/DeleteRoles/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Integer id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

