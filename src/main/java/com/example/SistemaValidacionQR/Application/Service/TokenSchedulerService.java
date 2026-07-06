package com.example.SistemaValidacionQR.Application.Service;

import com.example.SistemaValidacionQR.Domein.Entitys.QrToken;
import com.example.SistemaValidacionQR.Domein.Repository.IQrTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenSchedulerService {

    private final IQrTokenRepository qrTokenRepository;

    public TokenSchedulerService(IQrTokenRepository qrTokenRepository){
        this.qrTokenRepository = qrTokenRepository;
    }

    @Scheduled(fixedRate = 600000)
    public void revocarTokensExpirados() {

        List<QrToken> tokensExpirados =
                qrTokenRepository
                        .findByRevocadoFalseAndFechaExpiracionBefore(
                                LocalDateTime.now()
                        );

        if (tokensExpirados.isEmpty()) {
            return;
        }

        tokensExpirados.forEach(token -> {
            token.setRevocado(true);
            token.setUpdatedAt(LocalDateTime.now());
        });

        qrTokenRepository.saveAll(tokensExpirados);

        System.out.println(
                "Tokens revocados: " +
                        tokensExpirados.size()
        );


    }
}
