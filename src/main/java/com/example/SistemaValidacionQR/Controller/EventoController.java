package com.example.SistemaValidacionQR.Controller;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoRequest;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IEventoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final IEventoService eventoService;

    public EventoController(IEventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/CreateEvento")
    public ResponseEntity<EventoResponse> agregarEvento(@Valid @RequestBody EventoRequest request) {

        return ResponseEntity.ok(
                eventoService.agregarEvento(request)
        );
    }

    @PutMapping("/UpdateEvento/{id}")
    public ResponseEntity<EventoResponse> actualizarEvento(@PathVariable Integer id, @Valid @RequestBody EventoRequest request) {

        return ResponseEntity.ok(
                eventoService.actualizarEvento(
                        id,
                        request
                )
        );
    }

    @GetMapping("/GetEventoById/{id}")
    public ResponseEntity<EventoResponse> getEventoById(@PathVariable Integer id) {

        return ResponseEntity.ok(
                eventoService.getEventoById(id)
        );
    }

    @GetMapping("/GetEventoByCodigo/{codigo}")
    public ResponseEntity<EventoResponse> getEventoByCodigo(@PathVariable String codigo) {

        return ResponseEntity.ok(
                eventoService.getEventoByCodigo(codigo)
        );
    }

    @GetMapping("/GetEventoByNombre/{nombre}")
    public ResponseEntity<List<EventoResponse>> getEventoByNombre(@PathVariable String nombre) {

        return ResponseEntity.ok(
                eventoService.getEventoByNombre(nombre)
        );
    }

    @GetMapping("/GetParticipantesEvento/{eventoId}")
    public ResponseEntity<List<AccesoResponse>> obtenerParticipantesEvento(@PathVariable Integer eventoId){

        return ResponseEntity.ok(

                eventoService
                        .getParticipantesEventoById(eventoId)

        );

    }

    @GetMapping("/GetAllEventos")
    public ResponseEntity<List<EventoResponse>> getAllEventos() {

        return ResponseEntity.ok(
                eventoService.getAllEventos()
        );
    }

    @GetMapping("/GetEventosActivos")
    public ResponseEntity<List<EventoResponse>> getEventosActivos() {

        return ResponseEntity.ok(
                eventoService.getEventosActivos()
        );
    }
}