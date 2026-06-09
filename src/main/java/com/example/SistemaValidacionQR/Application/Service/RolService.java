package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IRolService;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;
import com.example.SistemaValidacionQR.Domein.Interfaces.IRolRepository;
import org.springframework.stereotype.Service;

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
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Rol guardar(Rol rol) {

        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        return rolRepository.save(rol);
    }

    @Override
    public Rol actualizar(Integer id, Rol rolActualizado) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        rol.setNombre(rolActualizado.getNombre());
        rol.setDescripcion(rolActualizado.getDescripcion());

        return rolRepository.save(rol);
    }

    @Override
    public void eliminar(Integer id) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rol no encontrado"));

        rolRepository.delete(rol);
    }

    private RolResponse mapToResponse(Rol rol) {

        RolResponse response = new RolResponse();

        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        response.setDescripcion(rol.getDescripcion());

        return response;
    }
}
