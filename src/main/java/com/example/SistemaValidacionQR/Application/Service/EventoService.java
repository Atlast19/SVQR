package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Application.Dto.Acceso.AccesoResponse;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoRequest;
import com.example.SistemaValidacionQR.Application.Dto.Eventos.EventoResponse;
import com.example.SistemaValidacionQR.Application.Inferfaces.IEventoService;
import com.example.SistemaValidacionQR.Application.Inferfaces.IQrTokenService;
import com.example.SistemaValidacionQR.Domein.Entitys.Acceso;
import com.example.SistemaValidacionQR.Domein.Entitys.Evento;
import com.example.SistemaValidacionQR.Domein.Repository.IAccesoRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IEventoRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService implements IEventoService {

    private final IEventoRepository eventoRepository;
    private final IAccesoRepository accesoRepository;
    private final IQrTokenService qrTokenService;
    private final EventoSchedulerService eventoSchedulerService;

    public EventoService(IEventoRepository eventoRepository, IAccesoRepository accesoRepository, IQrTokenService qrTokenService, EventoSchedulerService eventoSchedulerService) {
        this.eventoRepository = eventoRepository;
        this.accesoRepository = accesoRepository;
        this.qrTokenService = qrTokenService;
        this.eventoSchedulerService = eventoSchedulerService;
    }

    @Override
    public EventoResponse agregarEvento(EventoRequest request) {

        if (eventoRepository.existsByCodigo(generarCodigoEvento())) {
            throw new RuntimeException(
                    "Ya existe un evento con el código: "
                            + generarCodigoEvento()
            );
        }

        Evento evento = new Evento();

        evento.setCodigo(generarCodigoEvento());
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setImagen(request.getImagen());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setFechaExpiracion(request.getFechaExpiracion());
        evento.setEstado(EstadoGenerico.ACTIVO);

        eventoRepository.save(evento);
        eventoSchedulerService.reprogramar();

        return mapToResponse(evento);
    }

    @Override
    public EventoResponse getEventoById(Integer id) {

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento no encontrado"
                        ));

        return mapToResponse(evento);
    }

    @Override
    public EventoResponse getEventoByCodigo(String codigo) {

        Evento evento = eventoRepository.findByCodigo(codigo)
                .filter(eventos -> eventos.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento no encontrado"
                        ));

        return mapToResponse(evento);
    }

    @Override
    public List<EventoResponse> getEventoByNombre(String nombre) {

        List<Evento> eventos = eventoRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(evento -> evento.getEstado() == EstadoGenerico.ACTIVO)
                .toList();

        return eventos.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AccesoResponse> getParticipantesEventoById(Integer eventoId) {


        Evento evento =
                eventoRepository.findById(eventoId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Evento no encontrado"
                                ));



        List<Acceso> accesos =
                accesoRepository.findByEventoId(eventoId);



        return accesos.stream()
                .map(acceso -> {


                    AccesoResponse response =
                            new AccesoResponse();



                    response.setId(
                            acceso.getId()
                    );


                    response.setUsuarioId(
                            acceso.getUsuario()
                                    .getId()
                    );


                    response.setMatricula(
                            acceso.getUsuario()
                                    .getMatricula()
                    );


                    response.setNombreCompleto(

                            acceso.getUsuario()
                                    .getNombre()
                                    + " "
                                    +
                                    acceso.getUsuario()
                                            .getApellido()

                    );


                    response.setFechaAcceso(
                            acceso.getFechaAcceso()
                    );


                    response.setEstado(
                            acceso.getEstado()
                    );


                    response.setIpAddress(
                            acceso.getIpAddress()
                    );


                    response.setDispositivo(
                            acceso.getDispositivo()
                    );



                    // Datos del evento

                    response.setEventoId(
                            evento.getId()
                    );


                    response.setNombreEvento(
                            evento.getNombre()
                    );


                    response.setCodigo(
                            evento.getCodigo()
                    );



                    return response;


                })
                .toList();

    }

    @Override
    public List<EventoResponse> getAllEventos() {

        return eventoRepository.findAll()
                .stream().filter(evento -> evento.getEstado() == EstadoGenerico.ACTIVO)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<EventoResponse> getEventosActivos() {

        return eventoRepository.findByEstado(
                        EstadoGenerico.ACTIVO
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void eliminarEvento(Integer id) {

        Evento evento = eventoRepository.findById(id)
                .filter(eventos -> eventos.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento no encontrado"
                        ));

        evento.setEstado(EstadoGenerico.INACTIVO);
        evento.setUpdatedAt(LocalDateTime.now());

        eventoRepository.save(evento);

        eventoSchedulerService.reprogramar();
    }

    @Override
    public EventoResponse actualizarEvento(Integer id, EventoRequest request) {
        Evento evento = eventoRepository.findById(id)
                .filter(eventos -> eventos.getEstado() == EstadoGenerico.ACTIVO)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evento no encontrado"
                        ));

        if (!evento.getCodigo().equals(generarCodigoEvento()) && eventoRepository.existsByCodigo(generarCodigoEvento())) {

            throw new RuntimeException(
                    "Ya existe un evento con el código: "
                            + generarCodigoEvento()
            );
        }

        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setImagen(request.getImagen());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setFechaExpiracion(request.getFechaExpiracion());

        qrTokenService.actualizarFechaExpiracionPorEvento(evento.getId(), evento.getFechaExpiracion());
        eventoRepository.save(evento);
        eventoSchedulerService.reprogramar();

        return mapToResponse(evento);
    }

    private String generarCodigoEvento() {

        Optional<Evento> ultimoEvento = eventoRepository.findTopByOrderByIdDesc();

        int consecutivo = 1;

        if (ultimoEvento.isPresent()) {

            String codigo = ultimoEvento.get().getCodigo();

            consecutivo = Integer.parseInt(codigo.substring(6)) + 1;
        }

        return String.format("EV%d%04d",
                LocalDate.now().getYear(),
                consecutivo);
    }

    private EventoResponse mapToResponse(Evento evento) {

        EventoResponse response =
                new EventoResponse();

        response.setId(evento.getId());
        response.setCodigo(evento.getCodigo());
        response.setNombre(evento.getNombre());
        response.setDescripcion(evento.getDescripcion());
        response.setImagen(evento.getImagen());
        response.setFechaInicio(evento.getFechaInicio());
        response.setFechaExpiracion(evento.getFechaExpiracion());
        response.setEstado(evento.getEstado());

        return response;
    }
}
