package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolRequest;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolUpdateRequest;
import com.example.SistemaValidacionQR.Application.Inferfaces.IRolService;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import com.example.SistemaValidacionQR.Domein.Repository.IRolRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RolService implements IRolService {

    private final IRolRepository rolRepository;

    public RolService(IRolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public RolResponse obtenerPorId(Integer id) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        return mapToResponse(rol);
    }

    @Override
    public RolResponse obtenerPorNombre(String nombre) {

        Rol rol = rolRepository.findByNombre(nombre)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        return mapToResponse(rol);
    }

    @Override
    public List<RolResponse> obtenerTodos() {

        return rolRepository.findAll()
                .stream()
                .filter(roles -> roles.getEstado() == EstadoGenerico.ACTIVO)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RolResponse guardar(RolRequest request) {

        if (rolRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        Rol rol = new Rol();

        rol.setNombre(request.getNombre());
        rol.setDescripcion(request.getDescripcion());
        rol.setEstado(EstadoGenerico.ACTIVO);

        Rol rolGuardado = rolRepository.save(rol);

        RolResponse response = new RolResponse();

        response.setId(rolGuardado.getId());
        response.setNombre(rolGuardado.getNombre());
        response.setDescripcion(rolGuardado.getDescripcion());
        response.setEstado(rolGuardado.getEstado());

        return response;
    }

    @Override
    public RolResponse actualizar(Integer id, RolUpdateRequest request) {

        Rol rol = rolRepository.findById(id)
                .filter(r -> r.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        if (request.getNombre() != null &&
                !request.getNombre().isBlank()) {

            rol.setNombre(request.getNombre());
        }

        if (request.getDescripcion() != null) {
            rol.setDescripcion(request.getDescripcion());
        }

        rol.setUpdatedAt(LocalDateTime.now());

        Rol rolActualizado = rolRepository.save(rol);

        return mapToResponse(rolActualizado);
    }

    @Override
    public void eliminar(Integer id) {

        Rol rol = rolRepository.findById(id)
                .filter(roles -> roles.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        rol.setEstado(EstadoGenerico.INACTIVO);
        rol.setUpdatedAt(LocalDateTime.now());

        rolRepository.save(rol);
    }

    private RolResponse mapToResponse(Rol rol) {

        RolResponse response = new RolResponse();

        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        response.setDescripcion(rol.getDescripcion());

        return response;
    }
}
