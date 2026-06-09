package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioRequest;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioResponse;
import com.example.SistemaValidacionQR.Application.Dto.Usuario.UsuarioUpdateRequest;


import java.util.List;


public interface IUsuarioService {

    List<UsuarioResponse> obtenerTodos();

    UsuarioResponse obtenerPorId(Integer id);

    UsuarioResponse obtenerPorEmail(String email);

    UsuarioResponse obtenerPorMatricula(String matricula);

    UsuarioResponse crearUsuario(UsuarioRequest request);

    UsuarioResponse actualizarUsuario(Integer id, UsuarioUpdateRequest request);

    void eliminar(Integer id);
}
