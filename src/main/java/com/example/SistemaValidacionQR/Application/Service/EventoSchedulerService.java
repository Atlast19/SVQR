package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Domein.Entitys.Evento;
import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Repository.IEventoRepository;
import com.example.SistemaValidacionQR.Domein.Repository.IQrTokenRepository;
import com.example.SistemaValidacionQR.Domein.enums.EstadoGenerico;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Service
public class EventoSchedulerService {

    private final TaskScheduler taskScheduler;

    private final IEventoRepository eventoRepository;

    private final IQrTokenRepository qrTokenRepository;

    private ScheduledFuture<?> tareaProgramada;

    public EventoSchedulerService(
            TaskScheduler taskScheduler,
            IEventoRepository eventoRepository,
            IQrTokenRepository qrTokenRepository) {

        this.taskScheduler = taskScheduler;
        this.eventoRepository = eventoRepository;
        this.qrTokenRepository = qrTokenRepository;
    }

    @PostConstruct
    public void iniciar() {

        System.out.println(
                "Inicializando scheduler..."
        );

        programarSiguienteEvento();

    }

    public synchronized void reprogramar() {

        cancelarProgramacion();

        programarSiguienteEvento();

    }

    private void cancelarProgramacion() {

        if (tareaProgramada != null &&
                !tareaProgramada.isCancelled()) {

            tareaProgramada.cancel(false);

        }

    }

    public void programarSiguienteEvento() {

        Optional<Evento> eventoOpt =
                eventoRepository.findFirstByEstadoOrderByFechaExpiracionAsc(
                        EstadoGenerico.ACTIVO
                );

        if (eventoOpt.isEmpty()) {

            System.out.println("No existen eventos activos.");

            return;
        }

        Evento evento = eventoOpt.get();

        System.out.println(
                "Próximo evento: "
                        + evento.getNombre()
        );

        System.out.println(
                "Expira: "
                        + evento.getFechaExpiracion()
        );

        tareaProgramada =
                taskScheduler.schedule(

                        () -> finalizarEvento(evento),

                        Timestamp.valueOf(
                                evento.getFechaExpiracion()
                        )

                );

    }

    private void finalizarEvento(Evento evento) {

        System.out.println(
                "Finalizando evento "
                        + evento.getNombre()
        );

        evento.setEstado(
                EstadoGenerico.INACTIVO
        );

        evento.setUpdatedAt(
                LocalDateTime.now()
        );

        eventoRepository.save(evento);

        List<QrToken> tokens =
                qrTokenRepository.findByEventoIdAndRevocadoFalse(
                        evento.getId()
                );

        tokens.forEach(token -> {

            token.setRevocado(true);

            token.setUpdatedAt(
                    LocalDateTime.now()
            );

        });

        qrTokenRepository.saveAll(tokens);

        System.out.println(
                "QR revocados: "
                        + tokens.size()
        );

        programarSiguienteEvento();

    }


}