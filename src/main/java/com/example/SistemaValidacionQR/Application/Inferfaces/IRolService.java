package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolRequest;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Application.Dto.Rol.RolUpdateRequest;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;

import java.util.List;

public interface IRolService {

    RolResponse obtenerPorId(Integer id);

    RolResponse obtenerPorNombre(String nombre);

    List<RolResponse> obtenerTodos();

    RolResponse guardar(RolRequest request);

    RolResponse actualizar(Integer id, RolUpdateRequest request);

    void eliminar(Integer id);
}
