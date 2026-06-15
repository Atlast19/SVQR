package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioRequest;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioResponse;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioUpdateRequest;
import com.example.SistemaValidacionQR.Application.Inferfaces.IUsuarioService;

import jakarta.validation.Valid;
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

    @GetMapping("/GetAllUsuarios") // ADMINISTRADOR
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {

        return ResponseEntity.ok(
                usuarioService.obtenerTodos()
        );
    }

    @GetMapping("/GetUsuarioById/{id}") //ADMINISTRADOR
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorId(id)
        );
    }

    @GetMapping("/Email/{email}") // ADMINSITRADOR
    public ResponseEntity<UsuarioResponse> obtenerPorEmail(@PathVariable String email) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorEmail(email)
        );
    }

    @GetMapping("/Matricula/{matricula}")// ADMINISTRADOR
    public ResponseEntity<UsuarioResponse> obtenerPorMatricula(@PathVariable String matricula) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorMatricula(matricula)
        );
    }

    @PostMapping("/CreateUsuarios") // TODOS
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(request));
    }

    @PutMapping("/UpdateUsuarioById/{id}") // ESTUDIANTE
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioUpdateRequest request) {

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, request)
        );
    }

    @DeleteMapping("/DeleteUsuario/{id}") // ADMINISTRAODR
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}