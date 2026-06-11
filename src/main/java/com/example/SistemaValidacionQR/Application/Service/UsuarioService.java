package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioRequest;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioResponse;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioUpdateRequest;
import com.example.SistemaValidacionQR.Application.Inferfaces.IUsuarioService;

import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import com.example.SistemaValidacionQR.Domein.Entitys.Usuario;
import com.example.SistemaValidacionQR.Domein.Repository.IRolRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IUsuarioRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository usuarioRepository, IRolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsuarioResponse> obtenerTodos() {

        return usuarioRepository.findAll()
                .stream()
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UsuarioResponse obtenerPorId(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse obtenerPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse obtenerPorMatricula(String matricula) {

        Usuario usuario = usuarioRepository.findByMatricula(matricula)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya existe");
        }

        if (usuarioRepository.existsByMatricula(request.getMatricula())) {
            throw new RuntimeException("La matrícula ya existe");
        }

        Rol rol = rolRepository.findById(3).orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();

        usuario.setMatricula(request.getMatricula());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());

        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        usuario.setEstado(EstadoGenerico.ACTIVO);

        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return mapToResponse(usuarioGuardado);
    }

    @Override
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioUpdateRequest request) {

        Usuario usuario = usuarioRepository.findById(id)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(2)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        if (request.getPassword() != null &&
                !request.getPassword().trim().isEmpty()) {

            usuario.setPasswordHash(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setRol(rol);

        usuario.setUpdatedAt(LocalDateTime.now());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return mapToResponse(usuarioActualizado);
    }

    @Override
    public void eliminar(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .filter(usuarios -> usuarios.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(EstadoGenerico.INACTIVO);
        usuario.setUpdatedAt(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {

        UsuarioResponse response =
                new UsuarioResponse();

        response.setId(usuario.getId());
        response.setMatricula(usuario.getMatricula());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());

        response.setEstado(
                usuario.getEstado().name()
        );

        response.setRol(
                usuario.getRol().getNombre()
        );

        return response;
    }
}
