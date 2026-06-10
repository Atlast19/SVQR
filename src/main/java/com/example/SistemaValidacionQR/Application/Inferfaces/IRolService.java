package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Rol.RolResponse;
import com.example.SistemaValidacionQR.Domein.Entitys.Rol;

import java.util.List;

public interface IRolService {

    RolResponse obtenerPorId(Integer id);

    RolResponse obtenerPorNombre(String nombre);

    List<RolResponse> obtenerTodos();

    RolResponse guardar(Rol rol);

    RolResponse actualizar(Integer id, Rol rol);

    void eliminar(Integer id);
}
