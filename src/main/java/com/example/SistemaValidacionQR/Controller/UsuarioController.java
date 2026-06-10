package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioRequest;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioResponse;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioUpdateRequest;
import com.example.SistemaValidacionQR.Application.Inferfaces.IUsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/GetAllUsuarios")
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {

        return ResponseEntity.ok(
                usuarioService.obtenerTodos()
        );
    }

    @GetMapping("/GetUsuarioById/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorId(id)
        );
    }

    @GetMapping("/Email/{email}")
    public ResponseEntity<UsuarioResponse> obtenerPorEmail(@PathVariable String email) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorEmail(email)
        );
    }

    @GetMapping("/Matricula/{matricula}")
    public ResponseEntity<UsuarioResponse> obtenerPorMatricula(@PathVariable String matricula) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorMatricula(matricula)
        );
    }

    @PostMapping("/CreateUsuarios")
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(request));
    }

    @PutMapping("/UpdateUsuarioById/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioUpdateRequest request) {

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, request)
        );
    }

    @DeleteMapping("/DeleteUsuario/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
