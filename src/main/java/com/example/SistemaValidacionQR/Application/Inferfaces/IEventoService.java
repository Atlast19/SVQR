package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoRequest;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoResponse;

import java.util.List;

public interface IEventoService {

    EventoResponse agregarEvento(EventoRequest request);

    EventoResponse getEventoById(Integer id);

    EventoResponse getEventoByCodigo(String codigo);

    List<EventoResponse> getEventoByNombre(String nombre);

    List<AccesoResponse> getParticipantesEventoById(Integer eventoId);

    List<EventoResponse> getAllEventos();

    List<EventoResponse> getEventosActivos();

    EventoResponse actualizarEvento(Integer id, EventoRequest request);
}
