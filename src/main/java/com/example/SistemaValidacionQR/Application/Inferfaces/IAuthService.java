package com.example.SistemaValidacionQR.Application.Inferfaces;

import com.example.SistemaValidacionQR.Application.Dto.Auth.AuthRequest;
import com.example.SistemaValidacionQR.Application.Dto.Auth.AuthResponse;

public interface IAuthService {

    AuthResponse login(AuthRequest request);
}
